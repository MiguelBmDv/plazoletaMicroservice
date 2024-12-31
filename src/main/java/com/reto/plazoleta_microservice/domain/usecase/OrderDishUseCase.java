package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import com.reto.plazoleta_microservice.domain.api.IOrderDishServicePort;
import com.reto.plazoleta_microservice.domain.model.OrderDish;
import com.reto.plazoleta_microservice.domain.spi.IOrderDishPersistencePort;

public class OrderDishUseCase implements IOrderDishServicePort {

    private final IOrderDishPersistencePort orderDishPersistencePort;

    public OrderDishUseCase(IOrderDishPersistencePort orderDishPersistencePort) {
        this.orderDishPersistencePort = orderDishPersistencePort;
    }

    @Override
    public void saveOrderDish(OrderDish orderDish) {
        orderDishPersistencePort.saveOrderDish(orderDish);
    }

    @Override
    public List<OrderDish> getAllOrderDish() {
        return orderDishPersistencePort.getAllOrderDish();
    }

    @Override
    public OrderDish getOrderDish(Long id) {
        return orderDishPersistencePort.getOrderDish(id);
    }

    @Override
    public void updateOrderDish(OrderDish orderDish) {
        orderDishPersistencePort.updateOrderDish(orderDish);
    }

    @Override
    public void deleteOrderDish(Long id) {
        orderDishPersistencePort.deleteOrderDish(id);
    }
}
