package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.reto.plazoleta_microservice.application.dto.UserResponse;

@FeignClient(name="user-microservice", url= "http://localhost:8090/staff/")
public interface UserFeignClient {

    @GetMapping("{documentNumber}")
    UserResponse getUserByDocumentNumber(@PathVariable Long documentNumber);

}
