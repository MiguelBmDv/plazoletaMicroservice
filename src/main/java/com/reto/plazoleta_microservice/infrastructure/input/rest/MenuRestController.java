package com.reto.plazoleta_microservice.infrastructure.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.plazoleta_microservice.application.dto.MenuRequest;
import com.reto.plazoleta_microservice.application.dto.MenuResponse;
import com.reto.plazoleta_microservice.application.handler.IMenuHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu/")
@RequiredArgsConstructor
public class MenuRestController {

    private final IMenuHandler menuHandler;

    @PostMapping()
    public ResponseEntity<Void> saveDishInMenu(@RequestBody MenuRequest menuRequest){
        menuHandler.saveDishInMenu(menuRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<List<MenuResponse>> getAllDishFrontMenu(){
        return ResponseEntity.ok(menuHandler.getAllDishesFromMenu());
    }

    @GetMapping("{idDish}")
    public ResponseEntity<MenuResponse> getDishFromMenu(@PathVariable(name="idDish") Long id){
        return ResponseEntity.ok(menuHandler.getDishFromMenu(id));
    }

    @PutMapping()
    public ResponseEntity <Void> updateDishInMenu(@RequestBody MenuRequest menuRequest){
        menuHandler.updateDishInMenu(menuRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{idDish}")
    public ResponseEntity <Void> deleteDishFromMenu(@PathVariable(name="idDish") Long id){
        menuHandler.deleteDishFromMenu(id);
        return ResponseEntity.noContent().build();
    }

}
