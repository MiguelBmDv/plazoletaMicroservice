package com.reto.plazoleta_microservice.application.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntryRequest {
    
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long chefId;
    private Long restaurantId;
    private List<OrderDishRequest> dishes;

}
