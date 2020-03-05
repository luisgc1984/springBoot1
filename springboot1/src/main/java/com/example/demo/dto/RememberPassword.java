package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RememberPassword implements Serializable{

	private static final long serialVersionUID = 1646443452287339818L;
	
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String userName;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}