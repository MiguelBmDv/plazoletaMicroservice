package com.reto.plazoleta_microservice.application.mapper;



import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.MenuRequest;
import com.reto.plazoleta_microservice.domain.model.Category;
import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

@Mapper(componentModel =  "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuRequestMapper {
    
    @Mapping(target = "photo", qualifiedByName = "base64ToByteArray")
    Photo toPhoto(MenuRequest menuRequest);
    @Named("base64ToByteArray")
    static byte[] base64ToByteArray(String base64Photo) {
        return Base64.getDecoder().decode(base64Photo);
    }

    Dish toDish(MenuRequest menuRequest);

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true) 

    Category toCategory(MenuRequest menuRequest);
    Restaurant toRestaurant(MenuRequest menuRequest);

}
