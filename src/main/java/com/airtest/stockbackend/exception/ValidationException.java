package com.airtest.stockbackend.exception;

public class ValidationException extends RuntimeException {
	public ValidationException(String message) {
		super(message);
	}

}