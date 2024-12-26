package com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.DishEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishEntitytMapper {

    DishEntity toEntity(Dish dish);

    Dish toDish(DishEntity dishEntity);

    List<Dish> toDishList(List<DishEntity> dishEntityList);

}
