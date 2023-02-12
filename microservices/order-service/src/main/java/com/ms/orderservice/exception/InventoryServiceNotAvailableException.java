package com.ms.orderservice.exception;

public class InventoryServiceNotAvailableException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = -7927205855726807792L;


	public InventoryServiceNotAvailableException(String message) {
		super(message);
	}
}
