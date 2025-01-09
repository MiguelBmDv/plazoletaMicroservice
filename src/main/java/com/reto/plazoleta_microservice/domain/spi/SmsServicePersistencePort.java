package com.reto.plazoleta_microservice.domain.spi;

public interface SmsServicePersistencePort {
    void sendSms(String toPhoneNumber, String message);
}
