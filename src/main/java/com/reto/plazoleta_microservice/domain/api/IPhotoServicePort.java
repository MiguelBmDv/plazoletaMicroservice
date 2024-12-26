package com.reto.plazoleta_microservice.domain.api;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Photo;

public interface IPhotoServicePort {

    Photo savePhoto(Photo photo);

    List<Photo> getAllPhotos();

    Photo getPhoto(String photoId);

    void updatePhoto(Photo photo);

    void deletePhoto(String photoId);

}
