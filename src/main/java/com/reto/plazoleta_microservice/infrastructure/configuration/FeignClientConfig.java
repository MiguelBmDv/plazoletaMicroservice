package com.reto.plazoleta_microservice.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;

import feign.RequestInterceptor;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return requestTemplate -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.getDetails() instanceof JwtSecurityContext) {
                JwtSecurityContext context = (JwtSecurityContext) authentication.getDetails();
                String token = context.getJwt();
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
