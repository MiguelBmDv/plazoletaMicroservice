package com.reto.plazoleta_microservice.infrastructure.output.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.OrderDishEntity;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

    List<OrderDishEntity> findByOrderId(Long orderId);
}
