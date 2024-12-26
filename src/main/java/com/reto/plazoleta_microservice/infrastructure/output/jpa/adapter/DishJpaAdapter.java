package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.domain.spi.IDishPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.exception.DishAlreadyExistsException;
import com.reto.plazoleta_microservice.infrastructure.exception.DishNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.DishEntity;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.DishEntitytMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IDishRespository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRespository dishRespository;
    private final DishEntitytMapper dishEntitytMapper;

    @Override
    public void saveDish(Dish dish) {
        if (dishRespository.findByName(dish.getName()).isPresent()){
            throw new DishAlreadyExistsException();
        }

        dishRespository.save(dishEntitytMapper.toEntity(dish));
    }

    @Override
    public List<Dish> getAllDish() {
        List<DishEntity> dishEntityList = dishRespository.findAll();
        if (dishEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return dishEntitytMapper.toDishList(dishEntityList);
    }
    @Override
    public Dish getDish(Long id) {
        return dishEntitytMapper.toDish(dishRespository.findById(id)
                .orElseThrow(DishNotFoundException::new)); 
    }
    @Override
    public void updateDish(Dish dish) {
        dishRespository.save(dishEntitytMapper.toEntity(dish));
    }
    @Override
    public void deleteDish(Long id) {
        dishRespository.deleteById(id);
    }

}
