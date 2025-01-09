package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.model.OrderDish;
import com.reto.plazoleta_microservice.domain.spi.IOrderDishPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.exception.OrderAlreadyActiveException;
import com.reto.plazoleta_microservice.infrastructure.exception.OrderNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.entity.OrderEntity;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.OrderDishEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IOrderDishRepository;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.repository.IOrderRespository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort, IOrderDishPersistencePort{
    
    private final IOrderRespository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDishEntityMapper orderDishEntityMapper;

    @Override
    public void saveOrderDish(OrderDish orderDish) {
        orderDishRepository.save(orderDishEntityMapper.toEntity(orderDish));
        
    }

    @Override
    public List<OrderDish> getAllOrderDish() {
        return orderDishEntityMapper.toOrderDishList(orderDishRepository.findAll());
    }

    @Override
    public OrderDish getOrderDish(Long orderId) {
        throw new UnsupportedOperationException("Unimplemented method 'getOrderDish'");
    }

    @Override
    public void updateOrderDish(OrderDish orderDish) {
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderDish'");
    }

    @Override
    public void deleteOrderDish(Long orderId) {
        orderDishRepository.deleteById(orderId);
    }

    @Override
    public void saveOrder(Order order) {
        validateOrderIsNotActive(order.getClientId());
        orderRepository.save(orderEntityMapper.toEntity(order));
        orderRepository.flush();
    }

    @Override
    public List<Order> getAllOrder() {
        return orderEntityMapper.toOrderList(orderRepository.findAll());
    }

    @Override
    public Order getOrder(Long id) {
        return orderEntityMapper.toOrder(orderRepository.findById(id)
            .orElseThrow(OrderNotFoundException::new));
    }

    @Override
    public Page<Order> getOrderByStatus(Long restaurantId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderEntityPage;

        if (status != null && !status.isEmpty()) {
            orderEntityPage = orderRepository.findByRestaurantIdAndStatus(restaurantId, status, pageable);
        } else {
            orderEntityPage = orderRepository.findByRestaurantId(restaurantId, pageable);
        }

        return orderEntityPage.map(orderEntityMapper::toOrder);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void validateOrderIsNotActive(Long clientId) {
        Optional<OrderEntity> existingOrder = orderRepository.findByClientIdAndStatusNotIn(
            clientId,
            Arrays.asList("ENTREGADO", "CANCELADO")
        );

        if (existingOrder.isPresent()) {
            throw new OrderAlreadyActiveException();
        }
    }


}
