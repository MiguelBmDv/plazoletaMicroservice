package com.reto.plazoleta_microservice.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantRequest;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

@Mapper(componentModel =  "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeRestaurantRequestMapper {

    EmployeeRestaurant toEmployeeRestaurant(EmployeeRestaurantRequest employeeRestaurantRequest);

    Restaurant toRestaurant (Restaurant restaurant);

}
