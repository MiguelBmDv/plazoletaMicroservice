package com.reto.plazoleta_microservice.infrastructure.output.jpa.utils;

import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.EmployeeRestauranEntity;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IEmployeeRestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeRestaurantUtils {

    private final IEmployeeRestaurantRepository employeeRestaurantRepository;

     public Long getRestaurantIdFromDocumentNumber(Long documentNumber) {
        EmployeeRestauranEntity employeeRestaurant = employeeRestaurantRepository.findByEmployeeDocument(documentNumber)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return employeeRestaurant.getRestaurantNit();
    }

}
