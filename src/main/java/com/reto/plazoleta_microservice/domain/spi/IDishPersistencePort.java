package com.reto.plazoleta_microservice.domain.spi;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Dish;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    List <Dish> getAllDish();

    Dish getDish(Long id);

    void updateDish(Dish dish);

    void deleteDish(Long id);

}
