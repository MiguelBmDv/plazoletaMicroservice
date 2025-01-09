package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import org.springframework.stereotype.Component;

import com.reto.plazoleta_microservice.domain.spi.SmsServicePersistencePort;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.SmsFeignClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmsServiceFeignAdapter implements SmsServicePersistencePort{

    private final SmsFeignClient smsFeignClient;

    @Override
    public void sendSms(String toPhoneNumber, String message) {
        smsFeignClient.sendSms(toPhoneNumber, message);
    }
}
