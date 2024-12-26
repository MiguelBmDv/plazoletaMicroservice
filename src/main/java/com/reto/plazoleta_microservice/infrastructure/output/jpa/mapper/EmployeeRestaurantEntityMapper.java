package com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.EmployeeRestauranEntity;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeRestaurantEntityMapper {

    EmployeeRestauranEntity toEntity(EmployeeRestaurant employeeRestaurant);

    EmployeeRestaurant toEmployeeRestaurant(EmployeeRestauranEntity employeeRestauranEntity);

    List<EmployeeRestaurant> toEmployeeRestaurantList(List <EmployeeRestauranEntity> employeeRestauranEntityList);
    
}
