package com.ms.orderservice.exception;

public class InsufficientQuantityException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -2372219396451998412L;



	public InsufficientQuantityException(String message) {
		super(message);
	}

}
