package com.reto.plazoleta_microservice.infrastructure.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.OrderEntity;

public interface IOrderRespository extends JpaRepository <OrderEntity, Long> {

    Optional<OrderEntity> findByClientId(Long clientId);

    Optional<OrderEntity> findByClientIdAndStatusNot(Long clientId, String string);

    Page<OrderEntity> findByRestaurantId(Long restaurantId, Pageable pageable);

    Page<OrderEntity> findByRestaurantIdAndStatus(Long restaurantId, String status, Pageable pageable);

}
