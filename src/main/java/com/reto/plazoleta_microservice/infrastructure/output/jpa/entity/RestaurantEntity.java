package com.reto.plazoleta_microservice.infrastructure.output.jpa.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is mandatory")
    private String name;
    @NotNull(message = "This field is mandatory")
    private String address;
    @NotNull(message = "This field is mandatory")
    private Long ownerId;
    @NotNull(message = "This field is mandatory")
    private String phone;
    @NotNull(message = "This field is mandatory")
    private String photoId;
    @NotNull(message = "This field is mandatory")
    private Long nit;
}
