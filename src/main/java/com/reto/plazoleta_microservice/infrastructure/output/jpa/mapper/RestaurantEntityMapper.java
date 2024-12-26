package com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.RestaurantEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {

    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    List <Restaurant> toRestaurantList(List<RestaurantEntity> restaurantEntityList);
}
