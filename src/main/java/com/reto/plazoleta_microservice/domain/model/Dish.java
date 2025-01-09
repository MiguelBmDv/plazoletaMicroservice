package com.reto.plazoleta_microservice.domain.model;


public class Dish {

    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Integer price;
    private Long restaurantId;
    private String photoId;
    private Boolean active;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Dish(Long id, String name, Long categoryId, String description, Integer price, Long restaurantId,
			String photoId, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.description = description;
		this.price = price;
		this.restaurantId = restaurantId;
		this.photoId = photoId;
		this.active = active;
	}
    
    
    
}
