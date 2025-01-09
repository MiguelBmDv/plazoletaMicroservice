package com.reto.plazoleta_microservice.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reto.plazoleta_microservice.application.mapper.TraceabilityMapper;
import com.reto.plazoleta_microservice.domain.api.ICategoryServicePort;
import com.reto.plazoleta_microservice.domain.api.IDishServicePort;
import com.reto.plazoleta_microservice.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.api.IOrderDishServicePort;
import com.reto.plazoleta_microservice.domain.api.IOrderServicePort;
import com.reto.plazoleta_microservice.domain.api.IPhotoServicePort;
import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IDishPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IOrderDishPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IPhotoPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.ITraceabilityPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IUserPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.SmsServicePersistencePort;
import com.reto.plazoleta_microservice.domain.usecase.CategoryUseCase;
import com.reto.plazoleta_microservice.domain.usecase.DishUseCase;
import com.reto.plazoleta_microservice.domain.usecase.EmployeeRestaurantUseCase;
import com.reto.plazoleta_microservice.domain.usecase.OrderDishUseCase;
import com.reto.plazoleta_microservice.domain.usecase.OrderUseCase;
import com.reto.plazoleta_microservice.domain.usecase.PhotoUseCase;
import com.reto.plazoleta_microservice.domain.usecase.RestaurantUseCase;
import com.reto.plazoleta_microservice.domain.utils.JwtUtilsDomain;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.EmployeeRestaurantJpaAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.OrderJpaAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.SmsServiceFeignAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.TraceabilityFeignAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.UserFeignAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.SmsFeignClient;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.TraceabilityFeignClient;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.UserFeignClient;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.DishEntitytMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.EmployeeRestaurantEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.OrderDishEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IDishRespository;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IEmployeeRestaurantRepository;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IOrderDishRepository;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IOrderRespository;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.adapter.PhotoMongodbAdapter;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.mapper.PhotoEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.repository.IPhotoRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IOrderRespository orderRespository;
    private final IOrderDishRepository orderDishRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDishEntityMapper orderDishEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final IDishRespository dishRespository;
    private final DishEntitytMapper dishEntitytMapper;
    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IPhotoRepository photoRepository;
    private final PhotoEntityMapper photoEntityMapper;
    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final EmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;
    private final UserFeignClient userFeignClient;
    private final JwtUtilsDomain jwtUtilsDomain;
    private final SmsFeignClient smsFeignClient;
    private final TraceabilityFeignClient traceabilityFeignClient;
    private final TraceabilityMapper traceabilityMapper;

    @Bean
    public SmsServicePersistencePort smsServicePersistencePort(){
        return new SmsServiceFeignAdapter(smsFeignClient);
    }

    @Bean 
    public ITraceabilityPersistencePort traceabilityPersistencePort(){
        return new TraceabilityFeignAdapter(traceabilityFeignClient,traceabilityMapper);
    }
    
    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserFeignAdapter(userFeignClient);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRespository, orderDishRepository, orderEntityMapper, orderDishEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase( traceabilityPersistencePort(),userPersistencePort(),smsServicePersistencePort(), orderPersistencePort(),jwtUtilsDomain, employeeRestaurantServicePort());
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort(){
        return new OrderJpaAdapter(orderRespository, orderDishRepository, orderEntityMapper, orderDishEntityMapper);
    }

    @Bean
    public IOrderDishServicePort orderDishServicePort(){
        return new OrderDishUseCase(orderDishPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishJpaAdapter(dishRespository, dishEntitytMapper);
    }

    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort());
    }

    @Bean
    public IPhotoPersistencePort photoPersistencePort() {
        return new PhotoMongodbAdapter(photoRepository, photoEntityMapper);
    }

    @Bean
    public IPhotoServicePort photoServicePort() {
        return new PhotoUseCase(photoPersistencePort());
    }

    @Bean
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort(){
        return new EmployeeRestaurantJpaAdapter(employeeRestaurantRepository, employeeRestaurantEntityMapper);
    }

    @Bean
    public IEmployeeRestaurantServicePort employeeRestaurantServicePort(){
        return new EmployeeRestaurantUseCase(employeeRestaurantPersistencePort(),userPersistencePort(),  restaurantPersistencePort(), jwtUtilsDomain);
    }

}
