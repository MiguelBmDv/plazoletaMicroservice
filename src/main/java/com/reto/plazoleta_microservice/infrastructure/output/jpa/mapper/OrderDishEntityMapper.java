package com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.domain.model.OrderDish;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.OrderDishEntity;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDishEntityMapper {

    OrderDishEntity toEntity(OrderDish orderDish);

    OrderDish toOrderDish(OrderDishEntity orderDishEntity);
    
    List<OrderDish> toOrderDishList(List<OrderDishEntity> orderDishEntityList);

}
