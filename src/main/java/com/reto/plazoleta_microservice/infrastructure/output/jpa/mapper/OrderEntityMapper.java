package com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.OrderEntity;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toOrder(OrderEntity orderEntity);

    List<Order> toOrderList(List<OrderEntity> orderEntityList);

}
