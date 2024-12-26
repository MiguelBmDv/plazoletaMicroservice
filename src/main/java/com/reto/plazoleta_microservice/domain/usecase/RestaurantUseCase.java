package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import com.reto.plazoleta_microservice.application.dto.UserResponse;
import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.UserFeignClient;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final UserFeignClient userFeignClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, UserFeignClient userFeignClient){
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant, Long ownerId) {
        UserResponse user = userFeignClient.getUserByDocumentNumber(ownerId);
        if (user != null && "OWNER".equals(user.getRol())){
            restaurant.setOwnerId(ownerId);
        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantPersistencePort.getAllRestaurant();
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantPersistencePort.getRestaurant(id);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantPersistencePort.updateRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantPersistencePort.deleteRestaurant(id);
    }
    
}
