package com.reto.plazoleta_microservice.domain.spi;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.OrderDish;

public interface IOrderDishPersistencePort {
    
    void saveOrderDish(OrderDish orderDish);
    
    List<OrderDish> getAllOrderDish();

    OrderDish getOrderDish(Long orderId);

    void updateOrderDish(OrderDish orderDish);

    void deleteOrderDish(Long orderId);

}
