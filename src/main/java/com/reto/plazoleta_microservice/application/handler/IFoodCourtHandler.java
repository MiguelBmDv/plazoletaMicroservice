package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.plazoleta_microservice.application.dto.FoodCourtRequest;
import com.reto.plazoleta_microservice.application.dto.FoodCourtResponse;
import com.reto.plazoleta_microservice.application.dto.FoodCourtUserResponse;

public interface IFoodCourtHandler {

    void saveRestaurantInFoodCourt (FoodCourtRequest foodCourtRequest);

    List<FoodCourtResponse> getAllRestaurantFromFoodCourt();

    FoodCourtResponse getRestaurantFromFoodCourt(Long nit);

    void updateRestaurantInFoodCourt (FoodCourtRequest foodCourtRequest);

    void deleteRestaurantFromFoodCourt (Long nit);

    Page <FoodCourtUserResponse> getAllRestaurantForUser(int page, int size);

}
