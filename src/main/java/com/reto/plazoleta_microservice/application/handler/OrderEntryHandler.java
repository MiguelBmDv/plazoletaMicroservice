package com.reto.plazoleta_microservice.application.handler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.reto.plazoleta_microservice.application.dto.OrderEntryRequest;
import com.reto.plazoleta_microservice.application.dto.OrderEntryResponse;
import com.reto.plazoleta_microservice.application.mapper.OrderEntryRequestMapper;
import com.reto.plazoleta_microservice.application.mapper.OrderEntryResponseMapper;
import com.reto.plazoleta_microservice.domain.api.IDishServicePort;
import com.reto.plazoleta_microservice.domain.api.IOrderDishServicePort;
import com.reto.plazoleta_microservice.domain.api.IOrderServicePort;
import com.reto.plazoleta_microservice.domain.exception.DishNotBelongToRestaurantException;
import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.model.OrderDish;
import com.reto.plazoleta_microservice.domain.utils.IdGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderEntryHandler implements IOrderEntryHandler{

    private final IOrderServicePort orderServicePort;
    private final IOrderDishServicePort orderDishServicePort;
    private final IDishServicePort dishServicePort;
    private final OrderEntryRequestMapper orderRequestMapper;
    private final OrderEntryResponseMapper orderResponseMapper;
    
    @Override
    public void saveOrderInEntry(OrderEntryRequest orderEntryRequest) {
        Long orderId = IdGenerator.generateId();
        Order order = orderRequestMapper.toOrder(orderEntryRequest);
        order.setId(orderId);
        order.setStatus("PENDIENTE");
        order.setDate(LocalDate.now());
        orderServicePort.saveOrder(order);
        List<OrderDish> orderDishes = orderRequestMapper.toOrderDishes(order.getId(), orderEntryRequest.getDishes());
        boolean allDishesBelongToRestaurant = orderDishes.stream()
            .allMatch(orderDish -> {
                Dish dish = dishServicePort.getDish(orderDish.getDishId());
                return dish.getRestaurantId().equals(order.getRestaurantId());
            });

        if (!allDishesBelongToRestaurant) {
            throw new DishNotBelongToRestaurantException();
        }
        orderDishes.forEach(orderDishServicePort::saveOrderDish);
    }

    @Override
    public List<OrderEntryResponse> getAllOrderFromEntry() {
        List<Order> orders = orderServicePort.getAllOrder();
        List<List<OrderDish>> allOrderDishes = new ArrayList<>();
        for (Order order : orders) {
            List<OrderDish> orderDishes = orderDishServicePort.getAllOrderDish().stream()
                .filter(orderDish -> orderDish.getOrderId().equals(order.getId()))
                .collect(Collectors.toList());
            allOrderDishes.add(orderDishes);
        }
        List<OrderEntryResponse> responseList = orderResponseMapper.toResponseList(orders, allOrderDishes);
        return responseList;
    }

    @Override
    public Page<OrderEntryResponse> getOrderByStatus(Long restaurantId, String status, int page, int size) {
        Page<Order> orderPage = orderServicePort.getOrderByStatus(restaurantId, status, page, size);

        List<List<OrderDish>> allOrderDishes = orderPage.getContent().stream()
            .map(order -> orderDishServicePort.getAllOrderDish().stream()
                    .filter(orderDish -> orderDish.getOrderId().equals(order.getId()))
                    .toList()
            ).toList();
        return orderResponseMapper.toResponsePage(orderPage, allOrderDishes);
    }

    @Override
    public OrderEntryResponse getOrderFromEntry(Long id) {
        Order order = orderServicePort.getOrder(id);
        List<OrderDish> orderDishes = orderDishServicePort.getAllOrderDish().stream()
                .filter(orderDish -> orderDish.getOrderId().equals(order.getId()))
                .collect(Collectors.toList());
        List<OrderEntryResponse> responseList = orderResponseMapper.toResponseList(List.of(order), List.of(orderDishes));
        return responseList.get(0);
    }

    @Override
    public void updateOrderInEntry(OrderEntryRequest orderEntryRequest) {
        Order oldOrder = orderServicePort.getOrder(orderEntryRequest.getId());
        Order updatedOrder = orderRequestMapper.toOrder(orderEntryRequest);
        updatedOrder.setId(oldOrder.getId()); 
        updatedOrder.setRestaurantId(oldOrder.getRestaurantId()); 
        updatedOrder.setDate(oldOrder.getDate()); 
        updatedOrder.setClientId(oldOrder.getClientId());

        orderServicePort.updateOrder(updatedOrder);
    }

    @Override
    public void deleteOrderFromEntry(Long id) {
        List<OrderDish> orderDishes = orderDishServicePort.getAllOrderDish().stream()
            .filter(orderDish -> orderDish.getOrderId().equals(id))
            .collect(Collectors.toList());
        orderDishes.forEach(orderDish -> orderDishServicePort.deleteOrderDish(orderDish.getId()));
        orderServicePort.deleteOrder(id);
    }

}
