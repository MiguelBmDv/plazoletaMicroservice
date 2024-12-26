package com.reto.plazoleta_microservice.application.mapper;

import java.util.Base64;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.FoodCourtResponse;
import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FoodCourtResponseMapper {

    @Mapping(target = "photo", qualifiedByName = "byteArrayToBase64")
    @Mapping(source = "restaurant.id", target = "id")
    FoodCourtResponse toResponse(Restaurant restaurant, Photo photo);

    @Named("byteArrayToBase64")
    static String byteArrayToBase64(byte[] byteArrayPhoto) {
        return Base64.getEncoder().encodeToString(byteArrayPhoto);
    }

    FoodCourtResponse toResponse (Restaurant restaurant);

    default List<FoodCourtResponse> toResponseList(List<Restaurant> restaurantList, List <Photo> photoList){
        return restaurantList.stream()
            .map(restaurant -> {
                FoodCourtResponse foodCourtResponse = new FoodCourtResponse();
                foodCourtResponse.setId(restaurant.getId());
                foodCourtResponse.setName(restaurant.getName());
                foodCourtResponse.setAddress(restaurant.getAddress());
                foodCourtResponse.setOwnerId(restaurant.getOwnerId());
                foodCourtResponse.setPhone(restaurant.getPhone());
                foodCourtResponse.setPhoto(byteArrayToBase64(photoList.stream()
                        .filter(photo -> photo.getId().equals(restaurant.getPhotoId()))
                        .findFirst()
                        .orElse(null).getPhoto()));
                foodCourtResponse.setNit(restaurant.getNit());
                return foodCourtResponse;
            }).toList();
    }
}
