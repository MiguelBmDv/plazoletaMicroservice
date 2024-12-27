package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.plazoleta_microservice.application.dto.MenuRequest;
import com.reto.plazoleta_microservice.application.dto.MenuResponse;
import com.reto.plazoleta_microservice.application.dto.MenuUserResponse;

public interface IMenuHandler {

    void saveDishInMenu(MenuRequest menuRequest);

    List<MenuResponse> getAllDishesFromMenu();

    MenuResponse getDishFromMenu(Long id);

    void updateDishInMenu(MenuRequest menuRequest);

    void deleteDishFromMenu(Long id);

    Page<MenuUserResponse> getDishesByRestaurant(Long restaurantId, String category, int page, int size);

}
