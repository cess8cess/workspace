package com.nrc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nrc.exception.InternalException;
import com.nrc.exception.InvalidLineNumberException;
import com.nrc.exception.TicketCheckedException;
import com.nrc.exception.TicketNotFoundException;
import com.nrc.model.Ticket;
import com.nrc.model.TicketStatus;
import com.nrc.service.ITicketService;

@RestController
public class TicketController implements ITicketController {

	@Autowired
	ITicketService ticketService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nrc.controller.ITicketController#createTicket(int)
	 */
	@Override
	@PostMapping("/ticket")
	public Ticket createTicket(@RequestParam("lineNumber") int lineNumber) {
		// parameter validation
		if (lineNumber <= 0) {
			throw new InvalidLineNumberException();
		}

		return Optional.ofNullable(ticketService.createTicket(lineNumber)).orElseThrow(InternalException::new);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nrc.controller.ITicketController#getAllTickets()
	 */

	@Override
	@GetMapping("/ticket")
	public List<Ticket> getAllTickets() {
		return ticketService.getTickets();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nrc.controller.ITicketController#getTicketById(long)
	 */

	@Override
	@GetMapping("/ticket/{id}")
	public Ticket getTicketById(@PathVariable long id) {
		return Optional.ofNullable(ticketService.getTicketById(id)).orElseThrow(TicketNotFoundException::new);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nrc.controller.ITicketController#amendTicket(long, int)
	 */

	@Override
	@PutMapping("/ticket/{id}")
	public Ticket amendTicket(@PathVariable long id, @RequestParam("lineNumber") int lineNumber) {
		Ticket ticket = Optional.ofNullable(ticketService.getTicketById(id)).orElseThrow(TicketNotFoundException::new);

		// throw exception if ticket was checked
		if (ticket.isChecked()) {
			throw new TicketCheckedException();
		}

		return Optional.ofNullable(ticketService.amendTicketLines(ticket, lineNumber))
				.orElseThrow(InternalException::new);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nrc.controller.ITicketController#checkTicket(long)
	 */
	@Override
	@PutMapping("/status/{id}")
	public TicketStatus checkTicket(@PathVariable long id) {
		Ticket ticket = Optional.ofNullable(ticketService.getTicketById(id)).orElseThrow(TicketNotFoundException::new);

		return Optional.ofNullable(ticketService.checkTicket(ticket)).orElseThrow(InternalException::new);
	}

}
