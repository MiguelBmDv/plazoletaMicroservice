package com.reto.plazoleta_microservice.domain.model;

import java.time.LocalDateTime;


public class Traceability {

    private Long id;
    private Long orderId;
    private Long clientDocument;
    private String clientEmail;
    private LocalDateTime dateTime;
    private String lastStatus;
    private String newStatus;
    private Long employeeDocument;
    private String employeeEmail;
    
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
	public Long getClientDocument() {
		return clientDocument;
	}
	public void setClientDocument(Long clientDocument) {
		this.clientDocument = clientDocument;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	public Long getEmployeeDocument() {
		return employeeDocument;
	}
	public void setEmployeeDocument(Long employeeDocument) {
		this.employeeDocument = employeeDocument;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	
    public Traceability(Long id, Long orderId, Long clientDocument, String clientEmail, LocalDateTime dateTime, 
    String lastStatus, String newStatus, Long employeeDocument, String employeeEmail) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.clientDocument = clientDocument;
        this.clientEmail = clientEmail;
        this.dateTime = dateTime;
        this.lastStatus = lastStatus;
        this.newStatus = newStatus;
        this.employeeDocument = employeeDocument;
        this.employeeEmail = employeeEmail;
    }
    
}
