package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.plazoleta_microservice.domain.api.IOrderServicePort;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta_microservice.domain.utils.JwtUtilsDomain;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final JwtUtilsDomain jwtUtilsDomain;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, JwtUtilsDomain jwtUtilsDomain) {
        this.orderPersistencePort = orderPersistencePort;
        this.jwtUtilsDomain = jwtUtilsDomain;
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
        orderPersistencePort.updateOrder(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderPersistencePort.deleteOrder(id);
    }

}
