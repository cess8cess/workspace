package com.nrc.controller;

import java.util.List;

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

	/* (non-Javadoc)
	 * @see com.nrc.controller.ITicketController#createTicket(int)
	 */
	@Override
	@PostMapping("/ticket")
	public Ticket createTicket(@RequestParam("lineNumber") int lineNumber)  {
		// parameter validation
		if (lineNumber <= 0) {
			throw new InvalidLineNumberException();
		}

		Ticket ticket = ticketService.createTicket(lineNumber);

		// something wrong internal
		if (ticket == null) {
			throw new InternalException();
		}

		return ticket;
	}

	/* (non-Javadoc)
	 * @see com.nrc.controller.ITicketController#getAllTickets()
	 */

	@Override
	@GetMapping("/ticket")
	public List<Ticket> getAllTickets() {
		return ticketService.getTickets();
	}

	/* (non-Javadoc)
	 * @see com.nrc.controller.ITicketController#getTicketById(long)
	 */

	@Override
	@GetMapping("/ticket/{id}")
	public Ticket getTicketById(@PathVariable long id) {
		Ticket ticket = ticketService.getTicketById(id);
		
		// ticket not found
		if (ticket == null) {
			throw new TicketNotFoundException();
		}
		
		return ticket;
	}

	/* (non-Javadoc)
	 * @see com.nrc.controller.ITicketController#amendTicket(long, int)
	 */

	@Override
	@PutMapping("/ticket/{id}")
	public Ticket amendTicket(@PathVariable long id, @RequestParam("lineNumber") int lineNumber) {
		Ticket ticket = ticketService.getTicketById(id);

		// ticket not found
		if (ticket == null) {
			throw new TicketNotFoundException();
		}
		
		// throw exception if ticket was checked
		if (ticket.isChecked()) {
			throw new TicketCheckedException();
		}

		ticket = ticketService.amendTicketLines(ticket, lineNumber);

		// something wrong internal
		if (ticket == null) {
			throw new InternalException();
		}

		return ticket;
	}

	/* (non-Javadoc)
	 * @see com.nrc.controller.ITicketController#checkTicket(long)
	 */
	@Override
	@PutMapping("/status/{id}")
	public TicketStatus checkTicket(@PathVariable long id) {
		Ticket ticket = ticketService.getTicketById(id);

		// ticket not found
		if (ticket == null) {
			throw new TicketNotFoundException();
		}

		TicketStatus ticketStatus = ticketService.checkTicket(ticket);

		// something wrong internal
		if (ticketStatus == null) {
			throw new InternalException();
		}

		return ticketStatus;
	}

}
