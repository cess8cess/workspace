package com.nrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class TicketNotFoundException.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ticket Not Found")
public class TicketNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
