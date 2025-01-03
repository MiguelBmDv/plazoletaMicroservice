package com.reto.plazoleta_microservice.domain.constant;

import java.util.Map;

public class OrderStatusValidator {

    private OrderStatusValidator() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }
    
    private static final Map<String, String> VALID_TRANSITIONS = Map.of(
        "PENDIENTE", "EN PROCESO",
        "EN PROCESO", "LISTO",
        "LISTO", "ENTREGADO"
    );

    public static boolean isValidTransition(String currentStatus, String newStatus) {
        return VALID_TRANSITIONS.containsKey(currentStatus) &&
               VALID_TRANSITIONS.get(currentStatus).equals(newStatus);
    }
}
