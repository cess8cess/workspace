package com.nrc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrc.model.Ticket;
import com.nrc.model.TicketLine;
import com.nrc.model.TicketStatus;
import com.nrc.repo.TicketLineRepository;
import com.nrc.repo.TicketRepository;
import com.nrc.strategy.StandartResultCalculationStrategy;

@Service
public class TicketService implements ITicketService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	TicketLineRepository ticketLineRepository;

	@Override
	public Ticket createTicket(int numLines) throws IllegalArgumentException {
		if (numLines <= 0) {
			throw new IllegalArgumentException("invalid parameters");
		}
		Ticket ticket = new Ticket(produceLines(numLines));
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket amendTicketLines(Ticket ticket, int numLines) throws IllegalArgumentException, IllegalAccessError {
		if (numLines <= 0 || ticket == null) {
			throw new IllegalArgumentException("invalid parameters");
		}
		if (ticket.isChecked()) {
			throw new IllegalAccessError("checked ticket can not be amended");
		}

		ticket.setTicketLines(produceLines(numLines));
		return ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> getTickets() {
		return (List<Ticket>) ticketRepository.findAll();
	}

	@Override
	public Optional<Ticket> getTicketById(long id) {
		return ticketRepository.findById(id);
	}

	@Override
	public TicketStatus checkTicket(Ticket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException("invalid parameters");
		}

		// mark the ticket as checked
		ticket.setChecked(true);
		ticket = ticketRepository.save(ticket);

		// ticket line result calculation
		List<TicketLine> ticketLines = ticket.getTicketLines().stream()
				.map(StandartResultCalculationStrategy::calculate).sorted(StandartResultCalculationStrategy::compare)
				.collect(Collectors.toList());
		ticket.setTicketLines(ticketLines);

		return new TicketStatus(ticket);

	}

	/** Produce lines.
	 *
	 * @param indicates how many lines will be generated
	 * @return TicketLine List */
	private List<TicketLine> produceLines(int numLines) {
		List<TicketLine> lines = new ArrayList<>();

		for (int i = 0; i < numLines; i++) {
			lines.add(new TicketLine(() -> new Random().nextInt(3)));
		}
		return lines;
	}

}
