package com.rewards.backend.exception;

public class CustomerRegistrationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomerRegistrationException(String message) {
        super(message);
    }
}