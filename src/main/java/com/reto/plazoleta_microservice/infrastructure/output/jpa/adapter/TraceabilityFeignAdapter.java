package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import com.reto.plazoleta_microservice.application.dto.TraceabilityRequest;
import com.reto.plazoleta_microservice.application.mapper.TraceabilityMapper;
import com.reto.plazoleta_microservice.domain.model.Traceability;
import com.reto.plazoleta_microservice.domain.spi.ITraceabilityPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.TraceabilityFeignClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements ITraceabilityPersistencePort {

    private final TraceabilityFeignClient traceabilityFeignClient;
    private final TraceabilityMapper traceabilityMapper;

    @Override
    public void saveTraceability(Traceability traceability) {
        TraceabilityRequest request = traceabilityMapper.toRequest(traceability);
        traceabilityFeignClient.saveTraceability(request);
    }

}
