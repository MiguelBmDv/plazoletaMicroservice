package com.reto.plazoleta_microservice.domain.utils;

import java.security.SecureRandom;

public class PinGenerator {

    private static final SecureRandom random = new SecureRandom();

    private PinGenerator() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no debe ser instanciada.");
    }

    public static String generatePin() {
        int pin = random.nextInt(900000) + 100000;
        return String.valueOf(pin);
    }
}
