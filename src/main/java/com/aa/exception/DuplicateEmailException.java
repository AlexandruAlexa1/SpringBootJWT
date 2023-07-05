package com.aa.exception;

public class DuplicateEmailException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateEmailException(String message) {
		super(message);
	}
}
