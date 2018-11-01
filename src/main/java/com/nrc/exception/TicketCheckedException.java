package com.nrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class TicketCheckedException.
 */
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Ticket Already Checked")
public class TicketCheckedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
