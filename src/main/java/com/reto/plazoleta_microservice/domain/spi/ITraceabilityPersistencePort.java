package com.reto.plazoleta_microservice.domain.spi;

import com.reto.plazoleta_microservice.domain.model.Traceability;

public interface ITraceabilityPersistencePort {
    void saveTraceability(Traceability traceability);
}
