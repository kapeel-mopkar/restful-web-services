package com.kaps.learning.rest.webservices.restfulwebservices.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserPostException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3833563382143945462L;

	public DuplicateUserPostException(String message) {
		super(message);
	}
	
}
