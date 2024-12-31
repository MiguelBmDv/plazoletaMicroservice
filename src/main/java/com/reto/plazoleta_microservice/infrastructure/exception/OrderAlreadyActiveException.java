package com.reto.plazoleta_microservice.infrastructure.exception;

public class OrderAlreadyActiveException extends RuntimeException {
    public OrderAlreadyActiveException() {
        super("The client already has an active order in progress.");
    }

}
