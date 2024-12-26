package com.reto.plazoleta_microservice.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class JwtSecurityContext {
    private Long documentNumber;
    private String jwt; 

    public JwtSecurityContext(Long documentNumber, String jwt) {
        this.documentNumber = documentNumber;
        this.jwt = jwt;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public String getJwt() {
        return jwt;
    }

    public static JwtSecurityContext getContext() {
        return (JwtSecurityContext) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
    
}

