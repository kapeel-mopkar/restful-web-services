package com.kaps.learning.rest.webservices.restfulwebservices.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserPostNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3833563382143945462L;

	public UserPostNotFoundException(String message) {
		super(message);
	}
	
}
