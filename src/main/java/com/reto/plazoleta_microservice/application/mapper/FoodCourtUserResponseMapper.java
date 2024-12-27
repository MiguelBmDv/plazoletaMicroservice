package com.reto.plazoleta_microservice.application.mapper;

import java.util.Base64;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.reto.plazoleta_microservice.application.dto.FoodCourtUserResponse;
import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FoodCourtUserResponseMapper {

    @Mapping(target = "photo", qualifiedByName = "byteArrayToBase64")
    @Mapping(source = "restaurant.name", target = "name")
    FoodCourtUserResponse toResponse(Restaurant restaurant, Photo photo);

    @Named("byteArrayToBase64")
    static String byteArrayToBase64(byte[] byteArrayPhoto) {
        return Base64.getEncoder().encodeToString(byteArrayPhoto);
    }

    FoodCourtUserResponse toResponse (Restaurant restaurant);

    default Page<FoodCourtUserResponse> toUserResponsePage(Page<Restaurant> restaurantPage, List<Photo> photoList) {
        List<FoodCourtUserResponse> responses = restaurantPage.getContent().stream()
            .map(restaurant -> {
                FoodCourtUserResponse response = new FoodCourtUserResponse();
                response.setName(restaurant.getName());
                response.setPhoto(byteArrayToBase64(photoList.stream()
                        .filter(photo -> photo.getId().equals(restaurant.getPhotoId()))
                        .findFirst()
                        .orElse(null).getPhoto()));
                return response;
            }).toList();

        return new PageImpl<>(responses, restaurantPage.getPageable(), restaurantPage.getTotalElements());
    }
    
}
