package com.reto.plazoleta_microservice.infrastructure.output.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reto.plazoleta_microservice.infrastructure.output.mongodb.entity.PhotoEntity;

public interface IPhotoRepository extends MongoRepository<PhotoEntity, String>{}
