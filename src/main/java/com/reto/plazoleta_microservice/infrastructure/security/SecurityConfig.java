package com.reto.plazoleta_microservice.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.jwt.JwtAuthorizationFilter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/home/**").hasAnyAuthority("USER")
                .requestMatchers("/orders/**").hasAnyAuthority("EMPLOYEE")
                .requestMatchers("/owner/food-court/**", "/owner/menu/**", "/owner/restaurant-staff/**").hasAnyAuthority("OWNER")
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated() 
            )
            .addFilterBefore(new JwtAuthorizationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
