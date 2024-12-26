package com.reto.plazoleta_microservice.domain.api;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;

public interface IEmployeeRestaurantServicePort {

    void saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant);

    List<EmployeeRestaurant> getAllEmployeeRestaurant();

    EmployeeRestaurant getEmployeeRestaurant(Long restaurantNit);

    void updateEmployeeRestaurant(EmployeeRestaurant employeeRestaurant);

    void deleteEmployeeRestaurant(Long employeeDocument);

}
