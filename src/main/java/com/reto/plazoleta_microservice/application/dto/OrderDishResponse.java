package com.reto.plazoleta_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDishResponse {

    private Long id;
    private Long dishId;
    private Long orderId;
    private Integer quantity;
}
