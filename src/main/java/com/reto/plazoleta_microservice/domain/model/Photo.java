package com.reto.plazoleta_microservice.domain.model;

public class Photo {
    
    private String id;
    private byte[] photo;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public Photo(String id, byte[] photo) {
		super();
		this.id = id;
		this.photo = photo;
	}
    
    
}
