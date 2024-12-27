package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.reto.plazoleta_microservice.application.dto.UserResponse;
import com.reto.plazoleta_microservice.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.UserFeignClient;
import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;

public class EmployeeRestaurantUseCase implements IEmployeeRestaurantServicePort {

    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final UserFeignClient userFeignClient;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public EmployeeRestaurantUseCase (IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, UserFeignClient userFeignClient, IRestaurantPersistencePort restaurantPersistencePort){
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.userFeignClient = userFeignClient; 
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
        UserResponse user = userFeignClient.getUserByDocumentNumber(employeeRestaurant.getEmployeeDocument());
        employeeRestaurant.setEmployeeDocument(user.getDocumentNumber());
        Long ownerId = extractOwnerIdFromToken();
        Restaurant restaurant = findRestaurantByOwnerId(ownerId);
        employeeRestaurant.setRestaurantNit(restaurant.getNit());
        employeeRestaurantPersistencePort.saveEmployeeRestaurant(employeeRestaurant);
    }

    @Override
    public List<EmployeeRestaurant> getAllEmployeeRestaurant() {
        return employeeRestaurantPersistencePort.getAllEmployeeRestaurant();
    }

    @Override
    public EmployeeRestaurant getEmployeeRestaurant(Long restaurantNit) {
        return employeeRestaurantPersistencePort.getEmployeeRestaurant(restaurantNit);
    }

    @Override
    public void updateEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
        employeeRestaurantPersistencePort.updateEmployeeRestaurant(employeeRestaurant);
    }

    @Override
    public void deleteEmployeeRestaurant(Long employeeDocument) {
        employeeRestaurantPersistencePort.deleteEmployeeRestaurant(employeeDocument);
    }
    
    private Long extractOwnerIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            JwtSecurityContext jwtSecurityContext = JwtSecurityContext.getContext(); 

            if (jwtSecurityContext != null) {
                return jwtSecurityContext.getDocumentNumber(); 
            }
        }

        throw new IllegalStateException("No se pudo extraer el documentNumber del JWT");
    }

    private Restaurant findRestaurantByOwnerId(Long ownerId) {
        List<Restaurant> allRestaurants = restaurantPersistencePort.getAllRestaurant();
        return allRestaurants.stream()
                .filter(restaurant -> ownerId.equals(restaurant.getOwnerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró un restaurante para el propietario con ID: " + ownerId));
    }
}
