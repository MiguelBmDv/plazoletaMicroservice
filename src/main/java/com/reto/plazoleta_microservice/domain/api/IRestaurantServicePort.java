package com.reto.plazoleta_microservice.domain.api;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Restaurant;

public interface IRestaurantServicePort {

    void saveRestaurant (Restaurant restaurant, Long ownerId);

    List <Restaurant> getAllRestaurant();

    Restaurant getRestaurant(Long nit);

    void updateRestaurant(Restaurant restaurant);

    void deleteRestaurant(Long nit);

}
