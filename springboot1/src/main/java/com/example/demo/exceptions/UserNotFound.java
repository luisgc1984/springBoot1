package com.example.demo.exceptions;

public class UserNotFound extends Exception{

	private static final long serialVersionUID = 4621449587544098104L;
	
	public UserNotFound() {
		super("Usuario no encontrado");
	}
	
	public UserNotFound(String message) {
		super(message);
	}
}