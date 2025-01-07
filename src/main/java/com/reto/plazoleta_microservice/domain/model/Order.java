package com.reto.plazoleta_microservice.domain.model;

import java.time.LocalDate;

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
public class Order {

    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long chefId;
    private Long restaurantId;
    private String securityPin;

}
