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
@Table(name = "empleado_restaurante")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeRestauranEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is mandatory")
    private Long employeeDocument;
    @NotNull(message = "This field is mandatory")
    private Long restaurantNit;

}
