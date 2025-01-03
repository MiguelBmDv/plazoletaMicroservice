package com.reto.plazoleta_microservice.domain.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;

@Service
public class JwtUtilsDomain {
    
    public Long extractIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            JwtSecurityContext jwtSecurityContext = JwtSecurityContext.getContext();

            if (jwtSecurityContext != null) {
                return jwtSecurityContext.getDocumentNumber();
            }
        }

        throw new IllegalStateException("No se pudo extraer el documentNumber del JWT");
    }


}
