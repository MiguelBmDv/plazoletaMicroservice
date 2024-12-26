package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;


public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort){
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    public void saveRestaurant(Restaurant restaurant) {
        Long ownerId = extractOwnerIdFromToken();
        restaurant.setOwnerId(ownerId);
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantPersistencePort.getAllRestaurant();
    }

    @Override
    public Restaurant getRestaurant(Long nit) {
        return restaurantPersistencePort.getRestaurant(nit);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantPersistencePort.updateRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(Long nit) {
        restaurantPersistencePort.deleteRestaurant(nit);
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
    
}
