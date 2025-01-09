package com.reto.plazoleta_microservice.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraceabilityRequest {

    private Long id;
    private Long orderId;
    private Long clientDocument;
    private String clientEmail;
    private LocalDateTime dateTime;
    private String lastStatus;
    private String newStatus;
    private Long employeeDocument;
    private String employeeEmail;

}
