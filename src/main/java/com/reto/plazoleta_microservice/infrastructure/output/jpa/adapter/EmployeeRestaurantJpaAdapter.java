package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.EmployeeRestaurant;
import com.reto.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.exception.EmployeeAlreadyExistsException;
import com.reto.plazoleta_microservice.infrastructure.exception.EmployeeNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.plazoleta_microservice.infrastructure.exception.RestaurantNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.EmployeeRestauranEntity;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.EmployeeRestaurantEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IEmployeeRestaurantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeRestaurantJpaAdapter implements IEmployeeRestaurantPersistencePort{

    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final EmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;

    @Override
    public void saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
       if(employeeRestaurantRepository.findByEmployeeDocument(employeeRestaurant.getEmployeeDocument()).isPresent()){
            throw new EmployeeAlreadyExistsException();
       }

       employeeRestaurantRepository.save(employeeRestaurantEntityMapper.toEntity(employeeRestaurant));
    }

    @Override
    public List<EmployeeRestaurant> getAllEmployeeRestaurant() {
        List<EmployeeRestauranEntity> employeeRestauranEntityList = employeeRestaurantRepository.findAll();
        if (employeeRestauranEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return employeeRestaurantEntityMapper.toEmployeeRestaurantList(employeeRestauranEntityList);
    }

    @Override
    public EmployeeRestaurant getEmployeeRestaurant(Long restaurantNit) {
        return employeeRestaurantEntityMapper.toEmployeeRestaurant(employeeRestaurantRepository.findByRestaurantNit(restaurantNit)
                .orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public EmployeeRestaurant getRestaurantIdByEmployeeId(Long employeeDocument) {
        return employeeRestaurantEntityMapper.toEmployeeRestaurant(employeeRestaurantRepository.findByEmployeeDocument(employeeDocument)
                .orElseThrow(EmployeeNotFoundException::new));
    }

    @Override
    public void updateEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
        employeeRestaurantRepository.save(employeeRestaurantEntityMapper.toEntity(employeeRestaurant));
    }

    @Override
    public void deleteEmployeeRestaurant(Long employeeDocument) {
        employeeRestaurantRepository.deleteByEmployeeDocument(employeeDocument);
    }

}
