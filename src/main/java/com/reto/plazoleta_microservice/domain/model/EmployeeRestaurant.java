package com.reto.plazoleta_microservice.domain.model;

public class EmployeeRestaurant {

    private Long id;
    private Long employeeDocument;
    private Long restaurantNit;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmployeeDocument() {
		return employeeDocument;
	}
	public void setEmployeeDocument(Long employeeDocument) {
		this.employeeDocument = employeeDocument;
	}
	public Long getRestaurantNit() {
		return restaurantNit;
	}
	public void setRestaurantNit(Long restaurantNit) {
		this.restaurantNit = restaurantNit;
	}
	
	public EmployeeRestaurant(Long id, Long employeeDocument, Long restaurantNit) {
		super();
		this.id = id;
		this.employeeDocument = employeeDocument;
		this.restaurantNit = restaurantNit;
	}
    
    
}   
