package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.jwt;

public class JwtAuthenticationDetails {
    private String jwt;
    private Long documentNumber;

    public JwtAuthenticationDetails(String jwt, Long documentNumber) {
        this.jwt = jwt;
        this.documentNumber = documentNumber;
    }

    public String getJwt() {
        return jwt;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }
}

