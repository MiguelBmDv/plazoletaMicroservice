package com.reto.plazoleta_microservice.infrastructure.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.OrderEntity;

public interface IOrderRespository extends JpaRepository <OrderEntity, Long> {

    Optional<OrderEntity> findByClientId(Long clientId);

    Optional<OrderEntity> findByClientIdAndStatusNot(Long clientId, String string);

}
