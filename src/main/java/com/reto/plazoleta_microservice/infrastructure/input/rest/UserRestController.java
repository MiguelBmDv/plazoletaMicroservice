package com.reto.plazoleta_microservice.infrastructure.input.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reto.plazoleta_microservice.application.dto.FoodCourtUserResponse;
import com.reto.plazoleta_microservice.application.dto.MenuUserResponse;
import com.reto.plazoleta_microservice.application.handler.IFoodCourtHandler;
import com.reto.plazoleta_microservice.application.handler.IMenuHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/home/")
@RequiredArgsConstructor
public class UserRestController {

    private final IFoodCourtHandler foodCourtHandler;
    private final IMenuHandler menuHandler;

    @GetMapping("restaurants")
    public ResponseEntity<Page<FoodCourtUserResponse>> getAllRestaurantsForUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ) {
        Page<FoodCourtUserResponse> restaurants = foodCourtHandler.getAllRestaurantForUser(page, size);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public ResponseEntity<Page<MenuUserResponse>> getDishesByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(menuHandler.getDishesByRestaurant(restaurantId, category, page, size));
    }

}
