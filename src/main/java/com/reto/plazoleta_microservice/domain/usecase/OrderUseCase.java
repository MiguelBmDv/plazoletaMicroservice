package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.plazoleta_microservice.application.dto.UserResponse;
import com.reto.plazoleta_microservice.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.api.IOrderServicePort;
import com.reto.plazoleta_microservice.domain.constant.OrderStatusValidator;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta_microservice.domain.utils.JwtUtilsDomain;
import com.reto.plazoleta_microservice.domain.utils.PinGenerator;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.SmsClient;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.UserFeignClient;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IEmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final UserFeignClient userFeignClient;
    private final SmsClient smsClient;
    private final JwtUtilsDomain jwtUtilsDomain;

    public OrderUseCase( SmsClient smsClient, UserFeignClient userFeignClient, IOrderPersistencePort orderPersistencePort, JwtUtilsDomain jwtUtilsDomain,IEmployeeRestaurantServicePort employeeRestaurantServicePort ) {
        this.orderPersistencePort = orderPersistencePort;
        this.jwtUtilsDomain = jwtUtilsDomain;
        this.userFeignClient = userFeignClient;
        this.smsClient = smsClient;
        this.employeeRestaurantServicePort = employeeRestaurantServicePort;
    }

    @Override
    public void saveOrder(Order order) {
        Long clientId = jwtUtilsDomain.extractIdFromToken();
        order.setClientId(clientId);
        order.setChefId(null);
        orderPersistencePort.saveOrder(order);
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
        Long chefIdFromToken = jwtUtilsDomain.extractIdFromToken();
        EmployeeRestaurant employeeRestaurant = employeeRestaurantServicePort.getRestaurantIdByEmployeeId(chefIdFromToken); 
        validateRestaurantOwnership(existingOrder, employeeRestaurant);
        validateChefAssignment(existingOrder, chefIdFromToken);
        validateOrderIsNotDelivered(existingOrder);
        assignChefIfNecessary(existingOrder, order, chefIdFromToken);
        validateStatusTransition(existingOrder, order);
        if ("ENTREGADO".equals(order.getStatus())) {
            validateSecurityPin(existingOrder, order);
        }
        if ("LISTO".equals(order.getStatus())) {
            handleOrderReady(order);
        } else {
            orderPersistencePort.updateOrder(order);
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orderPersistencePort.deleteOrder(id);
    }

    private void validateRestaurantOwnership(Order existingOrder, EmployeeRestaurant employeeRestaurant) {
        if (!existingOrder.getRestaurantId().equals(employeeRestaurant.getRestaurantNit())) {
            throw new IllegalArgumentException("El empleado no pertenece al restaurante de este pedido.");
        }
    }
    
    private void validateChefAssignment(Order existingOrder, Long chefIdFromToken) {
        if (existingOrder.getChefId() != null && !existingOrder.getChefId().equals(chefIdFromToken)) {
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
    
    private void assignChefIfNecessary(Order existingOrder, Order order, Long chefIdFromToken) {
        if (existingOrder.getChefId() == null || existingOrder.getChefId() == 0) {
            order.setChefId(chefIdFromToken);
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

    private void handleOrderReady(Order order) {
        UserResponse user = userFeignClient.getUserByDocumentNumber(order.getClientId());
        String phoneNumber = user.getPhone();
        String pin = PinGenerator.generatePin(); 
        order.setSecurityPin(pin);
        orderPersistencePort.updateOrder(order);
        String message = "Tu pedido está listo. Usa este PIN para recogerlo: " + pin;
        smsClient.sendSms(phoneNumber, message);
    }

}
