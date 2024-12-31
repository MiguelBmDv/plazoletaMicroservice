package com.reto.plazoleta_microservice.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.plazoleta_microservice.application.dto.OrderDishResponse;
import com.reto.plazoleta_microservice.application.dto.OrderEntryResponse;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.model.OrderDish;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntryResponseMapper {

        OrderEntryResponse orderEntryResponse(Order order);
        OrderDishResponse toOrderDishResponse(OrderDish orderDish);

        default List<OrderDishResponse> toOrderDishResponseList(List<OrderDish> orderDishList) {
                return orderDishList.stream()
                        .map(this::toOrderDishResponse)
                        .toList();
        }

        default List<OrderEntryResponse> toResponseList(List<Order> orderList, List<List<OrderDish>> allOrderDishes) {
                return orderList.stream()
                        .map(order -> {
                            int index = orderList.indexOf(order);
                            List<OrderDish> orderDishes = allOrderDishes.get(index);
        
                            OrderEntryResponse orderEntryResponse = new OrderEntryResponse();
                            orderEntryResponse.setId(order.getId());
                            orderEntryResponse.setClientId(order.getClientId());
                            orderEntryResponse.setDate(order.getDate());
                            orderEntryResponse.setStatus(order.getStatus());
                            orderEntryResponse.setChefId(order.getChefId());
                            orderEntryResponse.setRestaurantId(order.getRestaurantId());
                            orderEntryResponse.setDishes(toOrderDishResponseList(orderDishes));
                            return orderEntryResponse;
                        }).toList();
            }

}
