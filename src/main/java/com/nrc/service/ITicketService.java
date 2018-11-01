
package com.nrc.service;

import java.util.List;

import com.nrc.model.Ticket;
import com.nrc.model.TicketStatus;

public interface ITicketService {
	
	
	/**
	 * Creates the ticket.
	 *
	 * @param numLines count of the lines in the ticket
	 * @return the ticket
	 * @throws IllegalArgumentException if numLines <= 0
	 */
	Ticket createTicket(int numLines) throws IllegalArgumentException;

	
	
	/**
	 * Amend ticket lines.
	 *
	 * @param ticket instance will be modified
	 * @param numLines count of the lines will be amended
	 * @return the ticket
	 * @throws IllegalArgumentException if numLines <= 0
	 * @throws IllegalAccessError checked ticket can not be amended
	 */
	
	Ticket amendTicketLines(Ticket ticket, int numLines) throws IllegalArgumentException, IllegalAccessError;

	
	/**
	 * Gets the tickets.
	 *
	 * @return the tickets
	 */
	List<Ticket> getTickets();

	/**
	 * Gets the ticket by id.
	 *
	 * @param id the id
	 * @return the ticket by id
	 */
	Ticket getTicketById(long id);

	/**
	 * Check the status of the ticket.
	 *
	 * @param ticket instance
	 * @return the ticketStatus instance
	 */
	TicketStatus checkTicket(Ticket ticket);

}
