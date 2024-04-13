package com.rewards.backend.exception;

public class ReferrerAlreadyExistsException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReferrerAlreadyExistsException(String message) {
        super(message);
    }

    public ReferrerAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
