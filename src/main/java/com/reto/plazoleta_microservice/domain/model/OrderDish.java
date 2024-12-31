package com.reto.plazoleta_microservice.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDish {

    private Long id;
    private Long orderId;
    private Long dishId;
    private Integer quantity;

}
