package com.reto.plazoleta_microservice.infrastructure.output.jpa.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orden")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderEntity {

    @Id
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long chefId;
    private Long restaurantId;
    private String securityPin;

}
