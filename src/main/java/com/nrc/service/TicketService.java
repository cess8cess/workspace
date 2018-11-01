package com.nrc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrc.context.NumberGenerationContext;
import com.nrc.context.ResultCalculationContext;
import com.nrc.model.Ticket;
import com.nrc.model.TicketLine;
import com.nrc.model.TicketStatus;
import com.nrc.repo.TicketLineRepository;
import com.nrc.repo.TicketRepository;
import com.nrc.strategy.StandartNumberGenerationStrategy;
import com.nrc.strategy.StandartResultCalculationStrategy;

@Service
public class TicketService implements ITicketService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	TicketLineRepository ticketLineRepository;

	@Autowired
	ResultCalculationContext resultCalculationContext;

	@Autowired
	NumberGenerationContext numberGenerationContext;

	@Override
	public Ticket createTicket(int numLines) throws IllegalArgumentException {
		if (numLines <= 0) {
			throw new IllegalArgumentException();
		}
		Ticket ticket = new Ticket(produceLines(numLines));
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket amendTicketLines(Ticket ticket, int numLines) throws IllegalArgumentException, IllegalAccessError {
		if (numLines <= 0) {
			throw new IllegalArgumentException();
		}
		if (ticket.isChecked()) {
			throw new IllegalAccessError();
		}
		ticket = new Ticket(produceLines(numLines), ticket.getId());
		return ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> getTickets() {
		return (List<Ticket>) ticketRepository.findAll();
	}

	@Override
	public Ticket getTicketById(long id) {
		return ticketRepository.findOne(id);
	}

	@Override
	public TicketStatus checkTicket(Ticket ticket) {
		// mark the ticket as checked
		ticket.setChecked(true);
		ticket = ticketRepository.save(ticket);

		// setting strategy
		resultCalculationContext.setCalculationStrategy(new StandartResultCalculationStrategy());

		// ticket line result calculation
		ticket.getTicketLines()
				.forEach(line -> line.setResult(resultCalculationContext.calculateResult(line.getNumbers())));

		// ticket lines sorting
		Collections.sort(ticket.getTicketLines(), (o1, o2) -> o2.getResult() - o1.getResult());

		return new TicketStatus(ticket);

	}

	/** Produce lines.
	 *
	 * @param indicates how many lines will be generated
	 * @return TicketLine List */
	private List<TicketLine> produceLines(int numLines) {
		List<TicketLine> lines = new ArrayList<>();
		// setting strategy
		numberGenerationContext.setGenerationStrategy(new StandartNumberGenerationStrategy());

		for (int i = 0; i < numLines; i++) {
			lines.add(new TicketLine(numberGenerationContext.generate()));
		}
		return lines;
	}

}
