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

import com.nrc.controller.ITicketController;
import com.nrc.exception.InvalidLineNumberException;
import com.nrc.exception.TicketCheckedException;
import com.nrc.exception.TicketNotFoundException;
import com.nrc.model.Ticket;
import com.nrc.model.TicketStatus;

@ContextConfiguration(classes = { LotteryApplication.class }, initializers = {
		ConfigFileApplicationContextInitializer.class })
@RunWith(SpringRunner.class)
public class TicketControllerTest {
	
	@Autowired
	ITicketController ticketController;
	
	@Test
	public void testCreateTicket() {
		Ticket ticket = ticketController.createTicket(3);
		assertNotNull(ticket);
		assertEquals(3, ticket.getTicketLines().size());
	}
	
	@Test(expected = InvalidLineNumberException.class)
	public void testCreateTicketInvalidLineNumberException() {
		ticketController.createTicket(-1);
	}
	
	@Test
	public void testGetAllTickets() {
		Ticket ticket = ticketController.createTicket(3);
		List<Ticket> ticketList = ticketController.getAllTickets();
		assertTrue(ticketList.size()>0);
	}
	
	@Test
	public void testGetTicketById() {
		Ticket ticket = ticketController.createTicket(3);
		Ticket fetchedTicket = ticketController.getTicketById(ticket.getId());
		assertEquals(fetchedTicket.getId(), ticket.getId());
	}
	
	@Test(expected = TicketNotFoundException.class)
	public void testGetTicketByIdTicketNotFoundException() {
		ticketController.getTicketById(0L);
	}

	
	@Test(expected = TicketNotFoundException.class)
	public void testAmendTicketTicketNotFoundException() {
		ticketController.amendTicket(0, 5);
	}
	
	@Test(expected = TicketCheckedException.class)
	public void testAmendTicketTicketCheckedException() {
		Ticket ticket = ticketController.createTicket(3);
		ticketController.checkTicket(ticket.getId());
		ticketController.amendTicket(ticket.getId(), 5);
	}
	
	@Test
	public void testAmendTicket() {
		Ticket ticket = ticketController.createTicket(3);
		ticket = ticketController.amendTicket(ticket.getId(), 5);
		assertEquals(5, ticket.getTicketLines().size());
		
	}
	
	@Test
	public void testCheckTicket() {
		Ticket ticket = ticketController.createTicket(3);
		TicketStatus ticketStatus = ticketController.checkTicket(ticket.getId());
		assertNotNull(ticketStatus);
		assertTrue(ticketStatus.isChecked());
		
	}
	
	@Test(expected = TicketNotFoundException.class)
	public void testCheckTicketTicketNotFoundException() {
		ticketController.checkTicket(0L);
		
	}
	
}
