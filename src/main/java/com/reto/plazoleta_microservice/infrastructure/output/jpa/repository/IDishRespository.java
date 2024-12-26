package com.reto.plazoleta_microservice.infrastructure.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.DishEntity;

public interface IDishRespository extends JpaRepository <DishEntity, Long>{
    Optional <DishEntity> findByName(String name);

    void deleteByName(String name);
}
