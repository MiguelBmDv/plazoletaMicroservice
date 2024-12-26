package com.reto.plazoleta_microservice.application.handler;

import org.springframework.stereotype.Component;

import com.reto.plazoleta_microservice.domain.constant.CategoryConstants;

@Component
public class CategoryResolver {
    public Long resolveCategoryId(String categoryName){
        switch (categoryName.toUpperCase()) {
            case "MAR":
                return CategoryConstants.SEA_FOOD;
            case "DESAYUNOS":
                return CategoryConstants.BREAKFAST;
            case "POSTRES":
                return CategoryConstants.DESSERTS;
            case "COMIDA RAPIDA":
                return CategoryConstants.FAST_FOOD;
            case "COMIDA SALUDABLE":
                return CategoryConstants.HEALTHY_FOOD;
            case "COMIDA ITALIANA":
                return CategoryConstants.ITALIAN_FOOD;
            case "COMIDA MEXICANA":
                return CategoryConstants.MEXICAN_FOOD;
            case "COMIDA VEGETARIANA":
                return CategoryConstants.VEGETARIAN_FOOD;
            default:
                throw new IllegalArgumentException("Invalid category: " + categoryName);
        }
    }
}
