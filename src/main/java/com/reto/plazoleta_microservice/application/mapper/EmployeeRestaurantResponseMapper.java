package com.reto.plazoleta_microservice.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantResponse;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EmployeeRestaurantResponseMapper {

    EmployeeRestaurantResponse toResponse(EmployeeRestaurant employeeRestaurant);

    default List<EmployeeRestaurantResponse> toResponseList(List<EmployeeRestaurant> employeeRestaurantList, List<Restaurant> restaurantList){
        return employeeRestaurantList.stream()
            .map( employeeRestaurant ->{
                EmployeeRestaurantResponse employeeRestaurantResponse = new EmployeeRestaurantResponse();
                employeeRestaurantResponse.setId(employeeRestaurant.getId());
                employeeRestaurantResponse.setEmployeeDocument(employeeRestaurant.getEmployeeDocument());
                employeeRestaurantResponse.setRestaurantNit(restaurantList.stream()
                    .filter(restaurant -> restaurant.getNit().equals(employeeRestaurant.getRestaurantNit()))
                    .findFirst()
                    .map(Restaurant::getNit)
                    .orElse(null));
                return employeeRestaurantResponse;
            }).toList();
    }

}
