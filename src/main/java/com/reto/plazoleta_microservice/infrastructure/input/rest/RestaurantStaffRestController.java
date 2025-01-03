package com.reto.plazoleta_microservice.infrastructure.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantRequest;
import com.reto.plazoleta_microservice.application.dto.EmployeeRestaurantResponse;
import com.reto.plazoleta_microservice.application.handler.IEmployeeRestaurantHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/owner/restaurant-staff/")
@RequiredArgsConstructor
public class RestaurantStaffRestController {

    private final IEmployeeRestaurantHandler employeeRestaurantHandler;

     @PostMapping()
    public ResponseEntity<Void> saveEmployeeInRestaurant(@RequestBody EmployeeRestaurantRequest employeeRestaurantRequest){ 
        employeeRestaurantHandler.saveEmployeeInRestaurant(employeeRestaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping()
    public ResponseEntity <List<EmployeeRestaurantResponse>> getAllEmployeeFromRestaurant(){
        return ResponseEntity.ok(employeeRestaurantHandler.getAllEmployeeFromRestaurant());
    }

    @GetMapping("{nitRestaurant}")
    public ResponseEntity<List<EmployeeRestaurantResponse>> getEmployeeFromRestaurant(@PathVariable(name = "nitRestaurant") Long nit){
        return ResponseEntity.ok(employeeRestaurantHandler.getEmployeeFromRestaurant(nit));
    }
    

    @DeleteMapping("{employeeDocument}")
    public ResponseEntity <Void> deleteEmployeeFromRestaurant(@PathVariable(name= "employeeDocument") Long employeeDocument){
        employeeRestaurantHandler.deleteEmployeeFromRestaurant(employeeDocument);
        return ResponseEntity.noContent().build();
    }

}
