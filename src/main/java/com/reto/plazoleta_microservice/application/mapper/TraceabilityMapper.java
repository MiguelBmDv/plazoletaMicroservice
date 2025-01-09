package com.reto.plazoleta_microservice.application.mapper;

import org.mapstruct.Mapper;

import com.reto.plazoleta_microservice.application.dto.TraceabilityRequest;
import com.reto.plazoleta_microservice.domain.model.Traceability;

@Mapper(componentModel = "spring")
public interface TraceabilityMapper {
    TraceabilityRequest toRequest (Traceability traceability);
}
