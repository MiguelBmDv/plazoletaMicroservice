package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import com.reto.plazoleta_microservice.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.domain.model.User;
import com.reto.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IUserPersistencePort;
import com.reto.plazoleta_microservice.domain.utils.JwtUtilsDomain;

public class EmployeeRestaurantUseCase implements IEmployeeRestaurantServicePort {

    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final JwtUtilsDomain jwtUtilsDomain;

    public EmployeeRestaurantUseCase (IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IUserPersistencePort userPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, JwtUtilsDomain jwtUtilsDomain){
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.userPersistencePort = userPersistencePort; 
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.jwtUtilsDomain = jwtUtilsDomain;
    }

    @Override
    public void saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
        User user = userPersistencePort.getUserByDocumentNumber(employeeRestaurant.getEmployeeDocument());
        employeeRestaurant.setEmployeeDocument(user.getDocumentNumber());
        Long ownerId = jwtUtilsDomain.extractIdFromToken();
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
    public EmployeeRestaurant getRestaurantIdByEmployeeId(Long employeeDocument) {
        return employeeRestaurantPersistencePort.getRestaurantIdByEmployeeId(employeeDocument);
    }

    @Override
    public void updateEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
        employeeRestaurantPersistencePort.updateEmployeeRestaurant(employeeRestaurant);
    }

    @Override
    public void deleteEmployeeRestaurant(Long employeeDocument) {
        employeeRestaurantPersistencePort.deleteEmployeeRestaurant(employeeDocument);
    }
    
    private Restaurant findRestaurantByOwnerId(Long ownerId) {
        List<Restaurant> allRestaurants = restaurantPersistencePort.getAllRestaurant();
        return allRestaurants.stream()
                .filter(restaurant -> ownerId.equals(restaurant.getOwnerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró un restaurante para el propietario con ID: " + ownerId));
    }

}
