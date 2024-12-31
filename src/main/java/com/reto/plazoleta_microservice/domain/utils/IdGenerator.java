package com.reto.plazoleta_microservice.domain.utils;

import java.util.UUID;

public class IdGenerator {
    
    private IdGenerator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Long generateId() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
}