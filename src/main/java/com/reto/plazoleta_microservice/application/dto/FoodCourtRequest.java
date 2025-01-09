package com.reto.plazoleta_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodCourtRequest {
    
    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String photo;
    private Long nit;

}
