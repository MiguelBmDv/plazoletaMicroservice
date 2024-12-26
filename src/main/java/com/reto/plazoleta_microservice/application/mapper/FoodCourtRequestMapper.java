package com.reto.plazoleta_microservice.application.mapper;

import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.FoodCourtRequest;
import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

import org.mapstruct.Named;

@Mapper(componentModel =  "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodCourtRequestMapper {

    Restaurant toRestaurant(FoodCourtRequest foodCourtRequest);
    
    @Mapping(target = "photo", qualifiedByName = "base64ToByteArray")
    Photo toPhoto(FoodCourtRequest foodCourtRequest);
    @Named("base64ToByteArray")
    static byte[] base64ToByteArray(String base64Photo) {
        return Base64.getDecoder().decode(base64Photo);
    }
}
