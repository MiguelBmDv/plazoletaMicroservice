package com.reto.plazoleta_microservice.domain.spi;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Order;



public interface IOrderPersistencePort {

    void saveOrder(Order order);

    List <Order> getAllOrder();

    Order getOrder(Long id);

    void updateOrder(Order order);

    void deleteOrder(Long id);

}
