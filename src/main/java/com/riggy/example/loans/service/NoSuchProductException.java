package com.riggy.example.loans.service;

public class NoSuchProductException extends RuntimeException {


	private static final long serialVersionUID = -787301824554524652L;
	
	
	public NoSuchProductException(String message){
		super(message);
	}

}
