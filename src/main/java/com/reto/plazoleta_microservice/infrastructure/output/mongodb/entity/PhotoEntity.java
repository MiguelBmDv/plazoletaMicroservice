package com.reto.plazoleta_microservice.infrastructure.output.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Document(collection = "photo")
@Data
public class PhotoEntity {
    @Id
    private String id;
    private byte[] photo;
}
