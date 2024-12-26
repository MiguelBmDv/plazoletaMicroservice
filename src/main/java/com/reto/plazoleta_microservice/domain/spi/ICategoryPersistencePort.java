package com.reto.plazoleta_microservice.domain.spi;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Category;

public interface ICategoryPersistencePort {

    Category saveCategory(Category category);

    List<Category> getAllCategory();

    Category getCategory(Long categoryId);

    void updateCategory(Category category);

    void deleteCategory(Long categoryId);

}
