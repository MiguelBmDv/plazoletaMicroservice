package com.reto.plazoleta_microservice.domain.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.reto.plazoleta_microservice.domain.api.IDishServicePort;
import com.reto.plazoleta_microservice.domain.model.Dish;
import com.reto.plazoleta_microservice.domain.model.Restaurant;
import com.reto.plazoleta_microservice.domain.spi.IDishPersistencePort;
import com.reto.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.security.JwtSecurityContext;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveDish(Dish dish) {
        Long ownerId = extractOwnerIdFromToken();
        Restaurant restaurant = findRestaurantByOwnerId(ownerId);
        dish.setRestaurantId(restaurant.getNit());
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public List<Dish> getAllDish() {
        return dishPersistencePort.getAllDish();
    }

    @Override
    public Page<Dish> getDishesByRestaurant(Long restaurantId, String category, int page, int size) {
        return dishPersistencePort.getDishesByRestaurant(restaurantId, category, page, size);
    }
    


    @Override
    public Dish getDish(Long id) {
        return dishPersistencePort.getDish(id);
    }

    @Override
    public void updateDish(Dish dish) {
        Long ownerId = extractOwnerIdFromToken();
        Dish existingDish = dishPersistencePort.getDish(dish.getId());
        validateDishOwnership(existingDish, ownerId);
        dishPersistencePort.updateDish(dish);
    }

    @Override
    public void deleteDish(Long id) {
        dishPersistencePort.deleteDish(id);
    }

    private Long extractOwnerIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            JwtSecurityContext jwtSecurityContext = JwtSecurityContext.getContext(); 

            if (jwtSecurityContext != null) {
                return jwtSecurityContext.getDocumentNumber(); 
            }
        }

        throw new IllegalStateException("No se pudo extraer el documentNumber del JWT");
    }

    private Restaurant findRestaurantByOwnerId(Long ownerId) {
        List<Restaurant> allRestaurants = restaurantPersistencePort.getAllRestaurant();
        return allRestaurants.stream()
                .filter(restaurant -> ownerId.equals(restaurant.getOwnerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró un restaurante para el propietario con ID: " + ownerId));
    }

    private void validateDishOwnership(Dish dish, Long ownerId) {
        Restaurant restaurant = findRestaurantByOwnerId(ownerId);
        if (!dish.getRestaurantId().equals(restaurant.getNit())) {
            throw new IllegalStateException("No tienes permisos para modificar este plato, ya que no pertenece a tu restaurante.");
        }
    }
    
}
