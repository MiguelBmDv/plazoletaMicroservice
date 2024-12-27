package com.reto.plazoleta_microservice.domain.spi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reto.plazoleta_microservice.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    void saveRestaurant (Restaurant restaurant);

    List <Restaurant> getAllRestaurant();

    Restaurant getRestaurant(Long nit);

    void updateRestaurant(Restaurant restaurant);

    void deleteRestaurant(Long nit);

    Page <Restaurant> getAllRestaurants(Pageable pageable);

}
