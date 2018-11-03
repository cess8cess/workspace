package com.nrc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.nrc.controller.TicketController;
import com.nrc.exception.InvalidLineNumberException;
import com.nrc.exception.TicketCheckedException;
import com.nrc.exception.TicketNotFoundException;
import com.nrc.model.Ticket;
import com.nrc.model.TicketLine;
import com.nrc.model.TicketStatus;
import com.nrc.service.ITicketService;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerMockTest {

	@Mock
	ITicketService ticketService;

	@InjectMocks
	TicketController ticketController;

	@Test(expected = InvalidLineNumberException.class)
	public void testCreateTicketInvalidLineNumberException() {
		ticketController.createTicket(0);
	}

	@Test
	public void testCreateTicket() {
		Optional<Ticket> fetchedTicket = produceTicket();
		when(ticketService.createTicket(3)).thenReturn(fetchedTicket.get());
		assertNotNull(ticketController.createTicket(3));

	}

	@Test
	public void testGetAllTickets() {
		when(ticketService.getTickets()).thenReturn(produceTickets(new int[][] { new int[] { 1, 0, 1 },
				new int[] { 0, 0, 0 }, new int[] { 2, 0, 1 }, new int[] { 2, 2, 1 } }));
		assertEquals(2, ticketController.getAllTickets().size());
	}

	@Test
	public void testGetTicketById() {
		when(ticketService.getTicketById(1L)).thenReturn(produceTicket());
		assertNotNull(ticketController.getTicketById(1L));

	}

	@Test(expected = TicketNotFoundException.class)
	public void testGetTicketByIdTicketNotFoundException() {
		ticketController.getTicketById(1L);
	}

	@Test
	public void testAmendTicket() {
		Optional<Ticket> fetchedTicket = produceTicket();
		Ticket amendedTicket = produceAmendedTicket();
		when(ticketService.getTicketById(1L)).thenReturn(fetchedTicket);
		when(ticketService.amendTicketLines(fetchedTicket.get(), 2)).thenReturn(amendedTicket);
		assertEquals(2, ticketController.amendTicket(1L, 2).getTicketLines().size());
	}

	@Test(expected = TicketNotFoundException.class)
	public void testAmendTicketTicketNotFoundException() {
		ticketController.amendTicket(1L, 2);
	}

	@Test(expected = TicketCheckedException.class)
	public void testAmendTicketCheckedException() {
		Optional<Ticket> fetchedTicket = produceTicket();
		fetchedTicket.get().setChecked(true);
		when(ticketService.getTicketById(1L)).thenReturn(fetchedTicket);
		ticketController.amendTicket(1L, 2);
	}

	@Test
	public void testCheckTicket() {
		Optional<Ticket> fetchedTicket = produceTicket();

		when(ticketService.getTicketById(1L)).thenReturn(fetchedTicket);
		when(ticketService.checkTicket(fetchedTicket.get())).thenReturn(produceTicketStatus(fetchedTicket.get()));
		assertNotNull(ticketController.checkTicket(1L));

	}

	@Test(expected = TicketNotFoundException.class)
	public void testCheckTicketTicketNotFoundException() {
		ticketController.checkTicket(1L);
	}

	private List<Ticket> produceTickets(int[][] lineNumbers) {
		List<Ticket> ticketList = new ArrayList<>();
		List<TicketLine> ticketLineList = new ArrayList<>();

		Arrays.stream(lineNumbers).forEach(t -> ticketLineList.add(new TicketLine(t)));
		ticketList.add(new Ticket(ticketLineList, 1L));
		ticketList.add(new Ticket(ticketLineList, 2L));

		return ticketList;
	}

	private Optional<Ticket> produceTicket() {
		return Optional.of(produceTickets(new int[][] { new int[] { 1, 0, 1 }, new int[] { 0, 0, 0 },
				new int[] { 2, 0, 1 }, new int[] { 2, 2, 1 } }).get(0));
	}

	private Ticket produceAmendedTicket() {
		return produceTickets(new int[][] { new int[] { 1, 0, 1 }, new int[] { 0, 0, 0 } }).get(0);
	}

	private TicketStatus produceTicketStatus(Ticket ticket) {
		ticket.setChecked(true);
		// ResultCalculationContext context = new ResultCalculationContext();
		// context.setCalculationStrategy(new StandartResultCalculationStrategy());

		// ticket.getTicketLines().forEach(line ->
		// line.setResult(context.calculateResult(line.getNumbers())));
		// Collections.sort(ticket.getTicketLines(), (o1, o2) -> o2.getResult() -
		// o1.getResult());

		return new TicketStatus(ticket);
	}

}
