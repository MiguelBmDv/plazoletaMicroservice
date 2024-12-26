package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;


import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Category;
import com.reto.plazoleta_microservice.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.exception.CategoryNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.CategoryEntity;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.ICategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort{

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Category saveCategory(Category category) {
        return categoryEntityMapper.toCategory(categoryRepository.save(categoryEntityMapper.toEntity(category)));
    }
    @Override
    public List<Category> getAllCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if(categoryEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryList(categoryEntityList);
    }
    @Override
    public Category getCategory(Long categoryId) {
        return categoryEntityMapper.toCategory(categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new));
    }
    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }
    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
