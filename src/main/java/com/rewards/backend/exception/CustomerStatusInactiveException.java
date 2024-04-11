package com.rewards.backend.exception;

public class CustomerStatusInactiveException extends Exception {

	private static final long serialVersionUID = 1L;

	
	public CustomerStatusInactiveException(String message) {
		super(message);
	}
}
