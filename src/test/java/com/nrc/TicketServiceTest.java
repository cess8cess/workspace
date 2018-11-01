package com.nrc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nrc.model.Ticket;
import com.nrc.model.TicketStatus;
import com.nrc.service.ITicketService;

@ContextConfiguration(classes = { LotteryApplication.class }, initializers = {
		ConfigFileApplicationContextInitializer.class })
@RunWith(SpringRunner.class)
public class TicketServiceTest {
	@Autowired
	ITicketService ticketService;
	
	@Test
	public void testCreateTicket() {
		Ticket ticket = ticketService.createTicket(3);
		assertNotNull(ticket);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTicketIllegalArgumentException() {
		ticketService.createTicket(-1);
	}

	@Test
	public void testGetTickets() {
		Ticket ticket = ticketService.createTicket(3);
		ticket = ticketService.createTicket(4);
		ticket = ticketService.createTicket(2);
		List<Ticket> tickets = ticketService.getTickets();
		assertTrue(tickets.size()>0);
	}
	
	@Test
	public void testGetTicketById() {
		Ticket ticket = ticketService.createTicket(3);
		Ticket retrievedTicket = ticketService.getTicketById(ticket.getId());
		assertEquals(ticket.getId(), retrievedTicket.getId());
	}
	

	@Test
	public void testAmendTicketLines() {
		Ticket ticket = ticketService.createTicket(3);
		ticket = ticketService.amendTicketLines(ticket, 2);
		assertEquals(2, ticket.getTicketLines().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAmendTicketLinesIllegalArgumentException() {
		Ticket ticket = ticketService.createTicket(3);
		ticket = ticketService.amendTicketLines(ticket, -1);
	}
	
	
	@Test(expected=IllegalAccessError.class)
	public void testAmendTicketLinesIllegalAccessError() {
		Ticket ticket = ticketService.createTicket(3);
		ticket.setChecked(true);
		ticket = ticketService.amendTicketLines(ticket, 4);
	}
	
	
	@Test
	public void testCheckTicketStatus() {
		Ticket ticket = ticketService.createTicket(3);
		TicketStatus ticketStatus = ticketService.checkTicket(ticket);
		assertNotNull(ticketStatus);
	}
	
	
}
