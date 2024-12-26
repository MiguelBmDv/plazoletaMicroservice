package com.reto.plazoleta_microservice.application.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantRequest;
import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantResponse;
import com.reto.plazoleta_microservice.application.mapper.EmployeeRestaurantRequestMapper;
import com.reto.plazoleta_microservice.application.mapper.EmployeeRestaurantResponseMapper;
import com.reto.plazoleta_microservice.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeRestaurantHandler implements IEmployeeRestaurantHandler {
    
    private final IEmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final EmployeeRestaurantRequestMapper employeeRestaurantRequestMapper;
    private final EmployeeRestaurantResponseMapper employeeRestaurantResponseMapper;
    
    @Override
    public void saveEmployeeInRestaurant(EmployeeRestaurantRequest employeeRestaurantRequest) {
        EmployeeRestaurant employeeRestaurant = employeeRestaurantRequestMapper.toEmployeeRestaurant(employeeRestaurantRequest);
        employeeRestaurantServicePort.saveEmployeeRestaurant(employeeRestaurant);
    }

    @Override
    public List<EmployeeRestaurantResponse> getAllEmployeeFromRestaurant() {
        return employeeRestaurantResponseMapper.toResponseList(employeeRestaurantServicePort.getAllEmployeeRestaurant(), restaurantServicePort.getAllRestaurant());
    }

    @Override
    public List<EmployeeRestaurantResponse> getEmployeeFromRestaurant(Long restaurantNit) {
        List<EmployeeRestaurant> employeeRestaurants = employeeRestaurantServicePort.getAllEmployeeRestaurant();
        List<Restaurant> restaurants = restaurantServicePort.getAllRestaurant();
        List<EmployeeRestaurant> filteredEmployees = employeeRestaurants.stream()
                .filter(employee -> employee.getRestaurantNit().equals(restaurantNit))
                .collect(Collectors.toList());
        List<EmployeeRestaurantResponse> employeeRestaurantResponses = employeeRestaurantResponseMapper.toResponseList(filteredEmployees, restaurants);

        return employeeRestaurantResponses;
    }

    @Override
    public void deleteEmployeeFromRestaurant(Long employeeDocument) {
        employeeRestaurantServicePort.deleteEmployeeRestaurant(employeeDocument);
    }

}
