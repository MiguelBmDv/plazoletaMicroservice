package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter;

import org.springframework.stereotype.Component;

import com.reto.plazoleta_microservice.application.dto.UserResponse;
import com.reto.plazoleta_microservice.domain.model.User;
import com.reto.plazoleta_microservice.domain.spi.IUserPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.client.UserFeignClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements IUserPersistencePort {

    private final UserFeignClient userFeignClient;

    @Override
    public User getUserByDocumentNumber(Long documentNumber) {
        UserResponse userResponse = userFeignClient.getUserByDocumentNumber(documentNumber);
        return toDomainUserInfo(userResponse);
    }

    public User toDomainUserInfo(UserResponse userResponse) {
        return new User(userResponse.getFirstName(),
                        userResponse.getLastName(),
                        userResponse.getDocumentNumber(),
                        userResponse.getPhone(),
                        userResponse.getDateOfBirth(),
                        userResponse.getEmail(),
                        userResponse.getRol());
    }
}
