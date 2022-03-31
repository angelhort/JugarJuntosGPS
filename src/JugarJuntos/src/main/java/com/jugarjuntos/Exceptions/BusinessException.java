package com.jugarjuntos.Exceptions;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public BusinessException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString() {
		return errorMessage;
	}
}
