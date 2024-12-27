package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.application.dto.FoodCourtRequest;
import com.reto.plazoleta_microservice.application.dto.FoodCourtResponse;
import com.reto.plazoleta_microservice.application.dto.FoodCourtUserResponse;
import com.reto.plazoleta_microservice.application.mapper.FoodCourtRequestMapper;
import com.reto.plazoleta_microservice.application.mapper.FoodCourtResponseMapper;
import com.reto.plazoleta_microservice.application.mapper.FoodCourtUserResponseMapper;
import com.reto.plazoleta_microservice.domain.api.IPhotoServicePort;
import com.reto.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.model.Restaurant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class FoodCourtHandler implements IFoodCourtHandler{

    private final IRestaurantServicePort restaurantServicePort;
    private final IPhotoServicePort photoServicePort;
    private final FoodCourtRequestMapper foodCourtRequestMapper;
    private final FoodCourtResponseMapper foodCourtResponseMapper;
    private final FoodCourtUserResponseMapper foodCourtUserResponseMapper;

    @Override
    public void saveRestaurantInFoodCourt(FoodCourtRequest foodCourtRequest) {
        Photo photo = photoServicePort.savePhoto(foodCourtRequestMapper.toPhoto(foodCourtRequest));
        Restaurant restaurant = foodCourtRequestMapper.toRestaurant(foodCourtRequest);
        restaurant.setPhotoId(photo.getId());
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public List<FoodCourtResponse> getAllRestaurantFromFoodCourt() {
        return foodCourtResponseMapper.toResponseList(restaurantServicePort.getAllRestaurant(), photoServicePort.getAllPhotos());
    }

    @Override
    public FoodCourtResponse getRestaurantFromFoodCourt(Long nit) {
        Restaurant restaurant = restaurantServicePort.getRestaurant(nit);
        return foodCourtResponseMapper.toResponse(restaurant, photoServicePort.getPhoto(restaurant.getPhotoId()));
    }

    @Override
    public void updateRestaurantInFoodCourt(FoodCourtRequest foodCourtRequest) {
        Restaurant oldRestaurant = restaurantServicePort.getRestaurant(foodCourtRequest.getNit());
        Photo newPhoto = foodCourtRequestMapper.toPhoto(foodCourtRequest);
        newPhoto.setId(oldRestaurant.getPhotoId());
        Restaurant newRestaurant = foodCourtRequestMapper.toRestaurant(foodCourtRequest);
        restaurantServicePort.updateRestaurant(newRestaurant);
    }

    @Override
    public void deleteRestaurantFromFoodCourt(Long nit) {
        Restaurant restaurant = restaurantServicePort.getRestaurant(nit);
        photoServicePort.deletePhoto(restaurant.getPhotoId());
        restaurantServicePort.deleteRestaurant(nit);
    }

    @Override
    public Page<FoodCourtUserResponse> getAllRestaurantForUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Restaurant> restaurantPage = restaurantServicePort.getAllRestaurants(pageable);
        List<Photo> photoList = photoServicePort.getAllPhotos(); 
        return foodCourtUserResponseMapper.toUserResponsePage(restaurantPage, photoList);
    }

}
