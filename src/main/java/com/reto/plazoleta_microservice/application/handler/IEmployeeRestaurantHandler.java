package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantRequest;
import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantResponse;

public interface IEmployeeRestaurantHandler {

    void saveEmployeeInRestaurant(EmployeeRestaurantRequest employeeRestaurantReques);

    List<EmployeeRestaurantResponse> getAllEmployeeFromRestaurant();

    List<EmployeeRestaurantResponse> getEmployeeFromRestaurant(Long restaurantNit);

    void deleteEmployeeFromRestaurant(Long employeeDocument);

}
