package com.reto.plazoleta_microservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRestaurant {

    private Long id;
    private Long employeeDocument;
    private Long restaurantNit;

}   
