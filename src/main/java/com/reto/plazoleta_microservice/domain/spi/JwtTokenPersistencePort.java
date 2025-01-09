package com.reto.plazoleta_microservice.domain.spi;

public interface JwtTokenPersistencePort {
    Long extractDocumentNumberFromToken();
}   
