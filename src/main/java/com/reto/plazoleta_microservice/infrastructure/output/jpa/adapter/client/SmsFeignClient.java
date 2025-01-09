package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reto.plazoleta_microservice.infrastructure.configuration.FeignClientConfig;

@FeignClient(name = "sms-microservice", url = "http://localhost:8092" , configuration = FeignClientConfig.class)
public interface SmsFeignClient {

    @RequestMapping("/sms/send")
    void sendSms(@RequestParam("toPhoneNumber") String toPhoneNumber,
                 @RequestParam("message") String message);
}
