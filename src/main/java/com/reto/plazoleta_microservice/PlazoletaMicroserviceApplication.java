package com.reto.plazoleta_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PlazoletaMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlazoletaMicroserviceApplication.class, args);
	}

}
