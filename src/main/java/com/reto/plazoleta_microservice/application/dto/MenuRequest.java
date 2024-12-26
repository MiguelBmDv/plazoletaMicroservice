package com.reto.plazoleta_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuRequest {

    private Long id;
    private String name;
    private String category;
    private String description;
    private Integer price;
    private Long restaurantId;
    private String photo;
    private Boolean active;

    @Override
    public String toString() {
        return "MenuRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                ", active=" + active +
                '}';
    }

}
