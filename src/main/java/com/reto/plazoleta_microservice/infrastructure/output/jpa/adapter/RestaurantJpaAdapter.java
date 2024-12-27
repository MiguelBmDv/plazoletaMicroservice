package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.plazoleta_microservice.infrastructure.exception.OwnerAlreadyExistsException;
import com.reto.plazoleta_microservice.infrastructure.exception.RestaurantAlreadyExistsException;
import com.reto.plazoleta_microservice.infrastructure.exception.RestaurantNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.RestaurantEntity;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    
    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    
    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if(restaurantRepository.findByNit(restaurant.getNit()).isPresent()){
            throw new RestaurantAlreadyExistsException();
        }
        if(restaurantRepository.findByOwnerId(restaurant.getOwnerId()).isPresent()){
            throw new OwnerAlreadyExistsException();
        }
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findAll();
        if (restaurantEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toRestaurantList(restaurantEntityList);
    }

    @Override
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        Page<RestaurantEntity> restaurantEntities = restaurantRepository.findAll(pageable);
        List<Restaurant> restaurants = restaurantEntities.getContent()
            .stream()
            .map(restaurantEntityMapper::toRestaurant)
            .collect(Collectors.toList());
        return new PageImpl<>(restaurants, pageable, restaurantEntities.getTotalElements());
    }

    @Override
    public Restaurant getRestaurant(Long nit) {
        return restaurantEntityMapper.toRestaurant(restaurantRepository.findByNit(nit)
                .orElseThrow(RestaurantNotFoundException::new));   
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public void deleteRestaurant(Long nit) {
        restaurantRepository.deleteByNit(nit);
    }

}
 