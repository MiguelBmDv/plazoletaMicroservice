package com.reto.plazoleta_microservice.domain.api;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.OrderDish;

public interface IOrderDishServicePort {
    
    void saveOrderDish(OrderDish orderDish);
    
    List<OrderDish> getAllOrderDish();

    OrderDish getOrderDish(Long orderId);

    void updateOrderDish(OrderDish orderDish);

    void deleteOrderDish(Long orderId);

}
