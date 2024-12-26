package com.reto.plazoleta_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuResponse {
    
    private Long id;
    private String name;
    private String category;
    private String description;
    private Integer price;
    private Long restaurantId;
    private String photo;
    private Boolean active;

}
