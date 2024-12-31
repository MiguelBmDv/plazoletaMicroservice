package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.reto.plazoleta_microservice.domain.api.IOrderServicePort;
import com.reto.plazoleta_microservice.domain.model.Order;
import com.reto.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public void saveOrder(Order order) {
        Long clientId = extractIdFromToken();
        order.setClientId(clientId);
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderPersistencePort.getAllOrder();
    }

    @Override
    public Order getOrder(Long id) {
        return orderPersistencePort.getOrder(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderPersistencePort.updateOrder(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderPersistencePort.deleteOrder(id);
    }

    private Long extractIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            JwtSecurityContext jwtSecurityContext = JwtSecurityContext.getContext(); 

            if (jwtSecurityContext != null) {
                return jwtSecurityContext.getDocumentNumber();   
            }
        }

        throw new IllegalStateException("No se pudo extraer el documentNumber del JWT");
    }
}
