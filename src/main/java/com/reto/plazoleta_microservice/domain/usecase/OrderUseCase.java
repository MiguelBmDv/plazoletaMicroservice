package com.reto.plazoleta_microservice.domain.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.plazoleta_microservice.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.api.IOrderServicePort;
import com.reto.plazoleta_microservice.domain.constant.OrderStatusValidator;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.model.Traceability;
import com.reto.plazoleta_microservice.domain.model.User;
import com.reto.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.ITraceabilityPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IUserPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.SmsServicePersistencePort;
import com.reto.plazoleta_microservice.domain.utils.IdGenerator;
import com.reto.plazoleta_microservice.domain.utils.JwtUtilsDomain;
import com.reto.plazoleta_microservice.domain.utils.PinGenerator;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IEmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final IUserPersistencePort userPersistencePort;
    private final SmsServicePersistencePort smsServicePersistencePort;
    private final JwtUtilsDomain jwtUtilsDomain;
    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public OrderUseCase( ITraceabilityPersistencePort traceabilityPersistencePort, IUserPersistencePort userPersistencePort, SmsServicePersistencePort smsServicePersistencePort, IOrderPersistencePort orderPersistencePort, JwtUtilsDomain jwtUtilsDomain,IEmployeeRestaurantServicePort employeeRestaurantServicePort ) {
        this.userPersistencePort = userPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.traceabilityPersistencePort = traceabilityPersistencePort;
        this.jwtUtilsDomain = jwtUtilsDomain;
        this.smsServicePersistencePort= smsServicePersistencePort;
        this.employeeRestaurantServicePort = employeeRestaurantServicePort;
    }

    @Override
    public void saveOrder(Order order) {
        Long clientId = jwtUtilsDomain.extractIdFromToken();
        User user = userPersistencePort.getUserByDocumentNumber(clientId);
        order.setClientId(clientId);
        order.setChefId(null);
        order.setStatus("PENDIENTE");
        order.setDate(LocalDate.now());
        order.setSecurityPin(null);
        orderPersistencePort.saveOrder(order);
        registerTraceability(order, user);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderPersistencePort.getAllOrder();
    }

    @Override
    public Order getOrder(Long id) {
        return orderPersistencePort.getOrder(id);
    }

    @Override
    public Page<Order> getOrderByStatus(Long restaurantId, String status, int page, int size) {
        return orderPersistencePort.getOrderByStatus(restaurantId, status, page, size);
    }

    @Override
    public void updateOrder(Order order) {
        Order existingOrder = orderPersistencePort.getOrder(order.getId());
        Long idFromToken = jwtUtilsDomain.extractIdFromToken();
        User user = userPersistencePort.getUserByDocumentNumber(idFromToken);
        User client = userPersistencePort.getUserByDocumentNumber(order.getClientId());
        if ("USER".equals(user.getRol())) {
            validateCancelOrder(existingOrder, order, user, idFromToken); 
            orderPersistencePort.updateOrder(order);
            registerTraceability(order, user);
        } else if ("EMPLOYEE".equals(user.getRol())) {
            EmployeeRestaurant employeeRestaurant = employeeRestaurantServicePort.getRestaurantIdByEmployeeId(idFromToken);
            validateRestaurantOwnership(existingOrder, employeeRestaurant);
            validateChefAssignment(existingOrder, idFromToken);
            validateOrderIsNotDelivered(existingOrder);
            assignChefIfNecessary(existingOrder, order, idFromToken);
            validateStatusTransition(existingOrder, order);

            if ("ENTREGADO".equals(order.getStatus())) {
                validateSecurityPin(existingOrder, order);
            }
    
            if ("LISTO".equals(order.getStatus())) {
                handleOrderReady(order, user);
                registerTraceabilityChef(existingOrder, order, user, client);
    
            } else {
                orderPersistencePort.updateOrder(order);
                registerTraceabilityChef(existingOrder, order, user, client);
            }

        } else {
            throw new IllegalArgumentException("Rol no reconocido.");
        }
        
    }

    @Override
    public void deleteOrder(Long id) {
        orderPersistencePort.deleteOrder(id);
    }
    

    private void registerTraceability(Order order, User user) {
        Traceability traceability = new Traceability(
        IdGenerator.generateId(),            
        order.getId(),                        
        order.getClientId(),                  
        user.getEmail(),            
        LocalDateTime.now(),   
        null,                     
        order.getStatus(),                   
        order.getChefId(),                   
        null                          
    );

    traceabilityPersistencePort.saveTraceability(traceability);
    }

    private void registerTraceabilityChef(Order oldOrder, Order order, User user, User client) {
        Traceability traceability = new Traceability(
        IdGenerator.generateId(),            
        oldOrder.getId(),                        
        oldOrder.getClientId(),                  
        client.getEmail(),            
        LocalDateTime.now(),   
        oldOrder.getStatus(),                     
        order.getStatus(),                   
        user.getDocumentNumber(),                   
        user.getEmail()                        
    );

    traceabilityPersistencePort.saveTraceability(traceability);
    }

    private void validateRestaurantOwnership(Order existingOrder, EmployeeRestaurant employeeRestaurant) {
        if (!existingOrder.getRestaurantId().equals(employeeRestaurant.getRestaurantNit())) {
            throw new IllegalArgumentException("El empleado no pertenece al restaurante de este pedido.");
        }
    }
    
    private void validateChefAssignment(Order existingOrder, Long idFromToken) {
        if (existingOrder.getChefId() != null && !existingOrder.getChefId().equals(idFromToken)) {
            throw new IllegalArgumentException("El chef no coincide con el chef asignado para este pedido.");
        }
    }
    
    private void validateOrderIsNotDelivered(Order existingOrder) {
        if ("ENTREGADO".equals(existingOrder.getStatus())) {
            throw new IllegalArgumentException("No se puede modificar un pedido que ya ha sido entregado.");
        }
    }
    
    private void validateStatusTransition(Order existingOrder, Order order) {
        if (!OrderStatusValidator.isValidTransition(existingOrder.getStatus(), order.getStatus())) {
            throw new IllegalArgumentException("Transición de estado no permitida.");
        }
    }
    
    private void assignChefIfNecessary(Order existingOrder, Order order, Long idFromToken) {
        if (existingOrder.getChefId() == null || existingOrder.getChefId() == 0) {
            order.setChefId(idFromToken);
            order.setStatus("EN PROCESO");
        } else {
            order.setChefId(existingOrder.getChefId());
        }
    }
    
    private void validateSecurityPin(Order existingOrder, Order order) {
        if (order.getSecurityPin() == null || !order.getSecurityPin().equals(existingOrder.getSecurityPin())) {
            throw new IllegalArgumentException("El PIN de seguridad es incorrecto.");
        }
    }
    
    private void handleOrderReady(Order order, User user) {
        String phoneNumber = user.getPhone();
        String pin = PinGenerator.generatePin();
        order.setSecurityPin(pin);
        orderPersistencePort.updateOrder(order);
        String message = "Tu pedido está listo. Usa este PIN para recogerlo: " + pin;
        smsServicePersistencePort.sendSms(phoneNumber, message);
    }
    
    private void validateCancelOrder(Order existingOrder, Order order, User user, Long idFromToken) {
        if (!idFromToken.equals(existingOrder.getClientId())) {
            throw new IllegalArgumentException("No tienes permiso para modificar este pedido.");
        }
    
        if ("CANCELADO".equals(order.getStatus())) {
            if (order.getStatus().equals(existingOrder.getStatus())){
                throw new IllegalArgumentException("No puedes modificar un pedido cancelado.");
            }
            if (!"PENDIENTE".equals(existingOrder.getStatus())) {
                String phoneNumber = user.getPhone();
                String message = "No puedes cancelar un pedido que no está en estado PENDIENTE.";
                smsServicePersistencePort.sendSms(phoneNumber, message);
                throw new IllegalArgumentException("No puedes cancelar un pedido que no está en estado PENDIENTE.");
            }
            order.setId(existingOrder.getId());
            order.setChefId(existingOrder.getChefId());
            order.setRestaurantId(existingOrder.getRestaurantId());
            order.setClientId(existingOrder.getClientId());
            order.setSecurityPin(existingOrder.getSecurityPin());
    
        } else if (order.getStatus() == null || !"CANCELADO".equals(order.getStatus())) {
            throw new IllegalArgumentException("El estado del pedido no es válido.");
        }
    }


    

}
