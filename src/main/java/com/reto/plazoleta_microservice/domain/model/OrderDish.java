package com.reto.plazoleta_microservice.domain.model;

public class OrderDish {

    private Long id;
    private Long orderId;
    private Long dishId;
    private Integer quantity;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getDishId() {
		return dishId;
	}
	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public OrderDish(Long id, Long orderId, Long dishId, Integer quantity) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.dishId = dishId;
		this.quantity = quantity;
	}

    
}
