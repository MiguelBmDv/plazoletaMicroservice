package com.reto.plazoleta_microservice.infrastructure.input.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.plazoleta_microservice.application.dto.FoodCourtRequest;
import com.reto.plazoleta_microservice.application.dto.FoodCourtResponse;
import com.reto.plazoleta_microservice.application.handler.IFoodCourtHandler;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/owner/food-court/")
@RequiredArgsConstructor
public class FoodCourtRestController {

    private final IFoodCourtHandler foodCourtHandler;

    @PostMapping()
    public ResponseEntity<Void> saveRestaurantInFoodCourt(@RequestBody FoodCourtRequest foodCourtRequest){ 
        foodCourtHandler.saveRestaurantInFoodCourt(foodCourtRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping()
    public ResponseEntity <List<FoodCourtResponse>> getAllRestaurantFromFoodCourt(){
        return ResponseEntity.ok(foodCourtHandler.getAllRestaurantFromFoodCourt());
    }

    @GetMapping("{nitRestaurant}")
    public ResponseEntity<FoodCourtResponse> getRestaurantFromFoodCourt(@PathVariable(name = "nitRestaurant") Long nit){
        return ResponseEntity.ok(foodCourtHandler.getRestaurantFromFoodCourt(nit));
    }
    
    @PutMapping()
    public ResponseEntity <Void> updateRestaurantInFoodCourt(@RequestBody FoodCourtRequest foodCourtRequest){
        foodCourtHandler.updateRestaurantInFoodCourt(foodCourtRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{nitRestaurant}")
    public ResponseEntity <Void> deleteRestaurantFromFoodCourt(@PathVariable(name= "nitRestaurant") Long nit){
        foodCourtHandler.deleteRestaurantFromFoodCourt(nit);
        return ResponseEntity.noContent().build();
    }

}
