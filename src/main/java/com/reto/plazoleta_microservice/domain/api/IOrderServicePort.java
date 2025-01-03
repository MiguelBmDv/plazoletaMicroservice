package com.reto.plazoleta_microservice.domain.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.plazoleta_microservice.domain.model.Order;



public interface IOrderServicePort {

    void saveOrder(Order order);

    List <Order> getAllOrder();

    Order getOrder(Long id);

    void updateOrder(Order order);

    void deleteOrder(Long id);
    
    Page<Order> getOrderByStatus(Long restaurantId, String status, int page, int size);

}
