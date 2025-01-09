package com.reto.plazoleta_microservice.domain.spi;

import com.reto.plazoleta_microservice.domain.model.User;

public interface IUserPersistencePort {
    User getUserByDocumentNumber(Long documentNumber);
}
