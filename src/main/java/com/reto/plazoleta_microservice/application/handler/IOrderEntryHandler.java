package com.reto.plazoleta_microservice.application.handler;

import java.util.List;

import com.reto.plazoleta_microservice.application.dto.OrderEntryRequest;
import com.reto.plazoleta_microservice.application.dto.OrderEntryResponse;

public interface IOrderEntryHandler {

    void saveOrderInEntry(OrderEntryRequest orderEntryRequest);

    List<OrderEntryResponse> getAllOrderFromEntry();

    OrderEntryResponse getOrderFromEntry(Long id);

    void updateOrderInEntry(OrderEntryRequest orderEntryRequest);

    void deleteOrderFromEntry(Long id);

}
