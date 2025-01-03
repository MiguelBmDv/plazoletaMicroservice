package com.reto.plazoleta_microservice.infrastructure.input.rest;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reto.plazoleta_microservice.application.dto.OrderEntryRequest;
import com.reto.plazoleta_microservice.application.dto.OrderEntryResponse;
import com.reto.plazoleta_microservice.application.handler.IOrderEntryHandler;
import com.reto.plazoleta_microservice.domain.utils.JwtUtilsDomain;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.utils.EmployeeRestaurantUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderEntryRestController {

    private final IOrderEntryHandler orderEntryHandler;
    private final JwtUtilsDomain jwtUtilsDomain;
    private final EmployeeRestaurantUtils employeeRestaurantUtils;

    @GetMapping()
    public ResponseEntity<Page<OrderEntryResponse>> getOrdersByRestaurant(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long documentNumber = jwtUtilsDomain.extractIdFromToken();
        Long restaurantId = employeeRestaurantUtils.getRestaurantIdFromDocumentNumber(documentNumber);

        return ResponseEntity.ok(orderEntryHandler.getOrderByStatus(restaurantId, status, page, size));
    }

    @PutMapping
    public ResponseEntity<Void> updateOrder(@RequestBody OrderEntryRequest request) {
        orderEntryHandler.updateOrderInEntry(request);
        return ResponseEntity.noContent().build();
    }

}
