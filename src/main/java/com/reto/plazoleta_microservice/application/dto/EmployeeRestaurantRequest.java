package com.reto.plazoleta_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRestaurantRequest {

    private Long id;
    private Long employeeDocument;
    private Long restaurantNit;
}
