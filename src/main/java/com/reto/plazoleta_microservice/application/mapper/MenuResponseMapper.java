package com.reto.plazoleta_microservice.application.mapper;

import java.util.List;
import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.reto.plazoleta_microservice.application.dto.MenuResponse;
import com.reto.plazoleta_microservice.application.dto.MenuUserResponse;
import com.reto.plazoleta_microservice.domain.model.Category;
import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuResponseMapper {

        @Mapping(target = "photo", qualifiedByName = "byteArrayToBase64")
        @Mapping(source = "dish.id", target = "id")
        MenuResponse toResponse(Dish dish, Photo photo);

        @Named("byteArrayToBase64")
        static String byteArrayToBase64(byte[] byteArrayPhoto) {
                return Base64.getEncoder().encodeToString(byteArrayPhoto);
        }
  
        MenuResponse toResponse(Dish dish);

        default List<MenuResponse> toResponseList(List<Dish> dishList, List<Category> categoryList, List<Restaurant> restaurantList, List<Photo> photoList){
                return dishList.stream()
                        .map(dish -> {
                                MenuResponse menuResponse = new MenuResponse();
                                menuResponse.setId(dish.getId());
                                menuResponse.setName(dish.getName());
                                menuResponse.setCategory(categoryList.stream()
                                        .filter(categories -> categories.getId().equals(dish.getCategoryId()))
                                        .findFirst()
                                        .map(Category::getName)
                                        .orElse(null)); 
                                menuResponse.setDescription(dish.getDescription());
                                menuResponse.setPrice(dish.getPrice());
                                menuResponse.setRestaurantId(restaurantList.stream()
                                        .filter(restaurant -> restaurant.getId().equals(dish.getRestaurantId()))
                                        .findFirst()
                                        .orElse(null).getId());
                                menuResponse.setPhoto(byteArrayToBase64(photoList.stream()
                                        .filter(photo -> photo.getId().equals(dish.getPhotoId()))
                                        .findFirst()
                                        .orElse(null).getPhoto()));
                                menuResponse.setActive(dish.getActive());
                                return menuResponse;
                        }).toList();
        }

        MenuUserResponse toUserResponse(Dish dish);

        default Page<MenuUserResponse> toResponsePage(Page<Dish> dishPage, List<Category> categoryList, List<Restaurant> restaurantList, List<Photo> photoList) {
                List<MenuUserResponse> responses = dishPage.getContent().stream()
                        .map(dish -> {
                        MenuUserResponse menuUserResponse = new MenuUserResponse();
                        menuUserResponse.setName(dish.getName());
                        menuUserResponse.setCategory(categoryList.stream()
                                .filter(category -> category.getId().equals(dish.getCategoryId()))
                                .findFirst()
                                .map(Category::getName)
                                .orElse(null));
                        menuUserResponse.setDescription(dish.getDescription());
                        menuUserResponse.setPrice(dish.getPrice());
                        menuUserResponse.setRestaurantId(restaurantList.stream()
                                .filter(restaurant -> restaurant.getNit().equals(dish.getRestaurantId()))
                                .findFirst()
                                .map(Restaurant::getNit)
                                .orElse(null));
                        menuUserResponse.setPhoto(byteArrayToBase64(photoList.stream()
                                .filter(photo -> photo.getId().equals(dish.getPhotoId()))
                                .findFirst()
                                .orElse(null)
                                .getPhoto()));
                        return menuUserResponse;
                        }).toList();

                return new PageImpl<>(responses, dishPage.getPageable(), dishPage.getTotalElements());
                }

}
