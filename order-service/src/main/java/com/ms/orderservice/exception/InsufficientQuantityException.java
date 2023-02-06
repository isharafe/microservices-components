package com.ms.orderservice.exception;

public class InsufficientQuantityException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -2372219396451998412L;



	public InsufficientQuantityException(String message) {
		super(message);
	}

}
