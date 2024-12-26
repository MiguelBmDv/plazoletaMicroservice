package com.reto.plazoleta_microservice.infrastructure.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.RestaurantEntity;

public interface IRestaurantRepository extends JpaRepository <RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByNit(Long nit);

    void deleteByNit(Long nit);

}
