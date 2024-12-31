package com.reto.plazoleta_microservice.infrastructure.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.plazoleta_microservice.application.dto.OrderEntryRequest;
import com.reto.plazoleta_microservice.application.dto.OrderEntryResponse;
import com.reto.plazoleta_microservice.application.handler.IOrderEntryHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderEntryRestController {

    private final IOrderEntryHandler orderEntryHandler;

    @PostMapping
    public ResponseEntity<Void> saveOrder(@RequestBody OrderEntryRequest request) {
        orderEntryHandler.saveOrderInEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderEntryResponse>> getAllOrders() {
        return ResponseEntity.ok(orderEntryHandler.getAllOrderFromEntry());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderEntryResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderEntryHandler.getOrderFromEntry(id));
    }

    @PutMapping
    public ResponseEntity<Void> updateOrder(@RequestBody OrderEntryRequest request) {
        orderEntryHandler.updateOrderInEntry(request);
        return ResponseEntity.noContent().build();
    }
}
