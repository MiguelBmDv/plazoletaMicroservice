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
@Table(name = "platos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is mandatory")
    private String name;
    @NotNull(message = "This field is mandatory")
    private Long categoryId;
    @NotNull(message = "This field is mandatory")
    private String description;
    @NotNull(message = "This field is mandatory")
    private Integer price;
    private Long restaurantId;
    @NotNull(message = "This field is mandatory")
    private String photoId;
    private Boolean active = true;

}
