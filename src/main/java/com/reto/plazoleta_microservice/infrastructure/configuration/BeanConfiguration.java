package com.reto.plazoleta_microservice.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reto.plazoleta_microservice.domain.api.IPhotoServicePort;
import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.spi.IPhotoPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.domain.usecase.PhotoUseCase;
import com.reto.plazoleta_microservice.domain.usecase.RestaurantUseCase;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.UserFeignClient;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.adapter.PhotoMongodbAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.mapper.PhotoEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.repository.IPhotoRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final UserFeignClient userFeignClient;
    private final IPhotoRepository photoRepository;
    private final PhotoEntityMapper photoEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort(), userFeignClient);
    }


    @Bean
    public IPhotoPersistencePort photoPersistencePort() {
        return new PhotoMongodbAdapter(photoRepository, photoEntityMapper);
    }

    @Bean
    public IPhotoServicePort photoServicePort() {
        return new PhotoUseCase(photoPersistencePort());
    }

}
