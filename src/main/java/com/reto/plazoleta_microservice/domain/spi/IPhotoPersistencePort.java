package com.reto.plazoleta_microservice.domain.spi;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Photo;

public interface IPhotoPersistencePort {

    Photo savePhoto(Photo photo);

    List<Photo> getAllPhotos();

    Photo getPhoto(String photoId);

    void updatePhoto(Photo photo);

    void deletePhoto(String photoId);

}
