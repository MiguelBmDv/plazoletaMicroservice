package com.reto.plazoleta_microservice.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.OrderDishRequest;
import com.reto.plazoleta_microservice.application.dto.OrderEntryRequest;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.model.OrderDish;


@Mapper(componentModel =  "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderEntryRequestMapper {
    @Mapping(target = "id", ignore = true)
    Order toOrder(OrderEntryRequest orderEntryRequest);
    OrderDish toOrderDish(OrderDishRequest orderDishRequest);

    default List<OrderDish> toOrderDishes(Long orderId, List<OrderDishRequest> dishesRequest) {
        return dishesRequest.stream()
                            .map(dishRequest -> {
                                OrderDish orderDish = toOrderDish(dishRequest); 
                                orderDish.setOrderId(orderId); 
                                return orderDish;
                            })
                            .collect(Collectors.toList());
    }
}
