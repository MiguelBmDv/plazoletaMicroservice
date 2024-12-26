package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = extractJwtFromRequest(request);
        if (jwt != null) {
            try {
                String username = jwtService.extractUsername(jwt);
                List<String> roles = jwtService.extractRoles(jwt);
                Long documentNumber = jwtService.extractDocumentNumber(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Crear UserDetails a partir del token
                    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(username)
                        .password("")
                        .authorities(roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()))
                        .build();

                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        JwtSecurityContext context = new JwtSecurityContext(documentNumber, jwt);
                        authToken.setDetails(context);  
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
