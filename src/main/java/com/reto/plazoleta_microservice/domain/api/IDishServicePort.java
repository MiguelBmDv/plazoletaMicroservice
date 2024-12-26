package com.reto.plazoleta_microservice.domain.api;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Dish;

public interface IDishServicePort {

    void saveDish(Dish dish);

    List <Dish> getAllDish();

    Dish getDish(Long id);

    void updateDish(Dish dish);

    void deleteDish(Long id);

}
