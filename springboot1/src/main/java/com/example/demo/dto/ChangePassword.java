package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class ChangePassword implements Serializable{
	
	private static final long serialVersionUID = 728642193424216693L;
	
	private Long id;
	@NotBlank(message="La password actual no puede estar vacia")
	private String currentPassword;
	@NotBlank(message="La nueva password no puede estar vacia")
	private String newPassword;
	@NotBlank(message="La confirmacion de la nueva password no puede estar vacia")
	private String confirmNewPassword;
	
	
	public ChangePassword() {
	}
	public ChangePassword(Long id) {
		super();
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
