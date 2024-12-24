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
public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String photoId;
    private Long nit;

}
