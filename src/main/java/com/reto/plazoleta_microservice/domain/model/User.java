package com.reto.plazoleta_microservice.domain.model;

import java.time.LocalDate;

public class User {

    private String firstName;
    private String lastName;
    private Long documentNumber;
    private String phone;
    private LocalDate dateOfBirth;
    private String email;
    private String rol;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}

	public User(String firstName, String lastName, Long documentNumber, String phone, LocalDate dateOfBirth,
			String email, String rol) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.documentNumber = documentNumber;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.rol = rol;
	}
}
