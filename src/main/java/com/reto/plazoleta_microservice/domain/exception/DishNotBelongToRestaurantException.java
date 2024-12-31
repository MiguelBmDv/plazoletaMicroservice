package com.reto.plazoleta_microservice.domain.exception;

public class DishNotBelongToRestaurantException extends RuntimeException {

    public DishNotBelongToRestaurantException() {
        super("Uno o más platos no pertenecen al restaurante seleccionado.");
    }

    public DishNotBelongToRestaurantException(String message) {
        super(message);
    }
}