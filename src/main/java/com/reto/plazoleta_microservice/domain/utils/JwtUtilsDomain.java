package com.reto.plazoleta_microservice.domain.utils;

import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.domain.spi.JwtTokenPersistencePort;

@Service
public class JwtUtilsDomain {
    private final JwtTokenPersistencePort jwtTokenPersistencePort;

    public JwtUtilsDomain(JwtTokenPersistencePort jwtTokenPersistencePort) {
        this.jwtTokenPersistencePort = jwtTokenPersistencePort;
    }

    public Long extractIdFromToken() {
        return jwtTokenPersistencePort.extractDocumentNumberFromToken();
    }
}