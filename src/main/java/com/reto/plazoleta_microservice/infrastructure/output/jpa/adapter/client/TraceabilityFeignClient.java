package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.reto.plazoleta_microservice.application.dto.TraceabilityRequest;
import com.reto.plazoleta_microservice.infrastructure.configuration.FeignClientConfig;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "traceability-microservice", url = "http://localhost:8093" , configuration = FeignClientConfig.class)
public interface TraceabilityFeignClient {

    @PostMapping("/traceability/")
    void saveTraceability(@RequestBody TraceabilityRequest traceabilityRequest)
    ;
}
