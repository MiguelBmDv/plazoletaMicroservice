package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import com.reto.plazoleta_microservice.application.dto.MenuRequest;
import com.reto.plazoleta_microservice.application.dto.MenuResponse;

public interface IMenuHandler {

    void saveDishInMenu(MenuRequest menuRequest);

    List<MenuResponse> getAllDishesFromMenu();

    MenuResponse getDishFromMenu(Long id);

    void updateDishInMenu(MenuRequest menuRequest);

    void deleteDishFromMenu(Long id);

}
