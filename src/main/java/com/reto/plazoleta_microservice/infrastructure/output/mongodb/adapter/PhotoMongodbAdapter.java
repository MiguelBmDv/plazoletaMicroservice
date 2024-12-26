package com.reto.plazoleta_microservice.infrastructure.output.mongodb.adapter;

import java.util.List;

import com.reto.plazoleta_microservice.domain.model.Photo;
import com.reto.plazoleta_microservice.domain.spi.IPhotoPersistencePort;
import com.reto.plazoleta_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.plazoleta_microservice.infrastructure.exception.PhotoNotFoundException;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.entity.PhotoEntity;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.mapper.PhotoEntityMapper;
import com.reto.plazoleta_microservice.infrastructure.output.mongodb.repository.IPhotoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhotoMongodbAdapter implements IPhotoPersistencePort{

    private final IPhotoRepository photoRepository;
    private final PhotoEntityMapper photoEntityMapper;

    @Override
    public Photo savePhoto(Photo photo) {
        return photoEntityMapper.toPhoto(photoRepository.save(photoEntityMapper.toEntity(photo)));
    }

    @Override
    public List<Photo> getAllPhotos() {
        List<PhotoEntity> photoEntityList = photoRepository.findAll();
        if (photoEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return photoEntityMapper.toPhotoList(photoEntityList);
    }

    @Override
    public Photo getPhoto(String photoId) {
        return photoEntityMapper.toPhoto(photoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new));
    }

    @Override
    public void updatePhoto(Photo photo) {
        photoRepository.save(photoEntityMapper.toEntity(photo));
    }

    @Override
    public void deletePhoto(String photoId) {
        photoRepository.deleteById(photoId);
    }

}
