package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import com.reto.plazoleta_microservice.application.dto.FoodCourtRequest;
import com.reto.plazoleta_microservice.application.dto.FoodCourtResponse;

public interface IFoodCourtHandler {

    void saveRestaurantInFoodCourt (FoodCourtRequest foodCourtRequest);

    List<FoodCourtResponse> getAllRestaurantFromFoodCourt();

    FoodCourtResponse getRestaurantFromFoodCourt(Long nit);

    void updateRestaurantInFoodCourt (FoodCourtRequest foodCourtRequest);

    void deleteRestaurantFromFoodCourt (Long nit);

}
