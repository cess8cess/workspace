package com.nrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * The Class InvalidLineNumberException.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid Input Parameter")
public class InvalidLineNumberException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
