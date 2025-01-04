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
        if (!existingOrder.getRestaurantId().equals(employeeRestaurant.getRestaurantNit())) {
            throw new IllegalArgumentException("El empleado no pertenece al restaurante de este pedido.");
        }
        if (existingOrder.getChefId() == null || existingOrder.getChefId() == 0) {
            order.setChefId(chefIdFromToken);
            order.setStatus("EN PROCESO");
        } else if (!existingOrder.getChefId().equals(chefIdFromToken)) {
            throw new IllegalArgumentException("El chefId ya está asignado y no puede ser modificado.");
        }
        if (!OrderStatusValidator.isValidTransition(existingOrder.getStatus(), order.getStatus())) {
            throw new IllegalArgumentException("Transición de estado no permitida.");
        }

        orderPersistencePort.updateOrder(order);
        if ("LISTO".equals(order.getStatus())){
            UserResponse user = userFeignClient.getUserByDocumentNumber(order.getClientId());
            String phoneNumber = user.getPhone();
            String pin = PinGenerator.generatePin(); 
            String message = "Tu pedido está listo. Usa este PIN para recogerlo: " + pin;

            smsClient.sendSms(phoneNumber, message);
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orderPersistencePort.deleteOrder(id);
    }

}
