package com.reto.plazoleta_microservice.infrastructure.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.EmployeeRestauranEntity;

public interface IEmployeeRestaurantRepository extends JpaRepository <EmployeeRestauranEntity, Long>{

    Optional <EmployeeRestauranEntity> findByEmployeeDocument(Long employeeDocument);

    Optional <EmployeeRestauranEntity> findByRestaurantNit(Long resturantNit);

    void deleteByEmployeeDocument(Long employeeDocument);
}

