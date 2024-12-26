package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.application.dto.MenuRequest;
import com.reto.plazoleta_microservice.application.dto.MenuResponse;
import com.reto.plazoleta_microservice.application.mapper.MenuRequestMapper;
import com.reto.plazoleta_microservice.application.mapper.MenuResponseMapper;
import com.reto.plazoleta_microservice.domain.api.ICategoryServicePort;
import com.reto.plazoleta_microservice.domain.api.IDishServicePort;
import com.reto.plazoleta_microservice.domain.api.IPhotoServicePort;
import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.Category;
import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.domain.model.Photo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuHandler implements IMenuHandler {
    
    private final IDishServicePort dishServicePort;
    private final ICategoryServicePort categoryServicePort;
    private final IPhotoServicePort photoServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final MenuRequestMapper menuRequestMapper;
    private final MenuResponseMapper menuResponseMapper;
    private final CategoryResolver categoryResolver;
    
    
    @Override
    public void saveDishInMenu(MenuRequest menuRequest) {
        
        Photo photo = photoServicePort.savePhoto(menuRequestMapper.toPhoto(menuRequest));
        Long categoryId = categoryResolver.resolveCategoryId(menuRequest.getCategory());
        Dish dish = menuRequestMapper.toDish(menuRequest);
        dish.setActive(true);
        dish.setPhotoId(photo.getId());
        dish.setCategoryId(categoryId);
        dish.setRestaurantId(menuRequest.getRestaurantId());
        dishServicePort.saveDish(dish);
    }

    @Override
    public List<MenuResponse> getAllDishesFromMenu() {
        return menuResponseMapper.toResponseList(dishServicePort.getAllDish(), categoryServicePort.getAllCategory(), restaurantServicePort.getAllRestaurant(), photoServicePort.getAllPhotos());
    }

    @Override
    public MenuResponse getDishFromMenu(Long id) {
        Dish dish = dishServicePort.getDish(id);
        Category category = categoryServicePort.getCategory(dish.getCategoryId());
        String categoryName = (category != null ) ? category.getName() : null;
        Photo photo = photoServicePort.getPhoto(dish.getPhotoId());

        MenuResponse menuResponse = menuResponseMapper.toResponse(dish, photo);
        menuResponse.setCategory(categoryName);
        return menuResponse;
    }

    @Override
    public void updateDishInMenu(MenuRequest menuRequest) {
        Dish oldDish = dishServicePort.getDish(menuRequest.getId());
        Dish newDish = menuRequestMapper.toDish(menuRequest);
        newDish.setId(oldDish.getId());
        newDish.setCategoryId(oldDish.getCategoryId());
        newDish.setName(oldDish.getName());
        newDish.setActive(oldDish.getActive());
        newDish.setPhotoId(oldDish.getPhotoId());
        newDish.setRestaurantId(oldDish.getRestaurantId());
        dishServicePort.updateDish(newDish);
    }

    @Override
    public void deleteDishFromMenu(Long id) {
        Dish dish = dishServicePort.getDish(id);
        photoServicePort.deletePhoto(dish.getPhotoId());
        dishServicePort.deleteDish(id);
    }

}
