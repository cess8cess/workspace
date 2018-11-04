package com.nrc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nrc.controller.TicketController;
import com.nrc.model.Ticket;
import com.nrc.model.TicketLine;
import com.nrc.model.TicketStatus;
import com.nrc.service.ITicketService;

@RunWith(SpringJUnit4ClassRunner.class)
public class TicketControllerMockTest {

	private MockMvc mockMvc;

	@Mock
	ITicketService ticketService;

	@InjectMocks
	TicketController ticketController;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
	}

	@Test
	public void testCreateTicketInvalidLineNumberException() throws Exception {
		mockMvc.perform(post("/lottery/ticket?lineNumber=0")).andExpect(status().isNotAcceptable());
	}

	@Test
	public void testCreateTicket() throws Exception {
		Optional<Ticket> fetchedTicket = produceTicket();
		when(ticketService.createTicket(4)).thenReturn(fetchedTicket.get());
		String expected = "{\"id\":1,\"ticketLines\":[{\"numbers\":[1,0,1]},{\"numbers\":[0,0,0]},{\"numbers\":[2,0,1]},{\"numbers\":[2,2,1]}],\"checked\":false}";
		String actual = mockMvc.perform(post("/lottery/ticket?lineNumber=4")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		assertEquals(expected, actual);

	}

	@Test
	public void testGetAllTickets() throws Exception {
		when(ticketService.getTickets()).thenReturn(produceTickets(new int[][] { new int[] { 1, 0, 1 },
				new int[] { 0, 0, 0 }, new int[] { 2, 0, 1 }, new int[] { 2, 2, 1 } }));
		String expected = "[{\"id\":1,\"ticketLines\":[{\"numbers\":[1,0,1]},{\"numbers\":[0,0,0]},{\"numbers\":[2,0,1]},{\"numbers\":[2,2,1]}],\"checked\":false},{\"id\":2,\"ticketLines\":[{\"numbers\":[1,0,1]},{\"numbers\":[0,0,0]},{\"numbers\":[2,0,1]},{\"numbers\":[2,2,1]}],\"checked\":false}]";
		String actual = mockMvc.perform(get("/lottery/ticket")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetTicketById() throws Exception {
		when(ticketService.getTicketById(1L)).thenReturn(produceTicket());
		String expected = "{\"id\":1,\"ticketLines\":[{\"numbers\":[1,0,1]},{\"numbers\":[0,0,0]},{\"numbers\":[2,0,1]},{\"numbers\":[2,2,1]}],\"checked\":false}";
		String actual = mockMvc.perform(get("/lottery/ticket/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetTicketByIdTicketNotFoundException() throws Exception {
		mockMvc.perform(get("/lottery/ticket/1")).andExpect(status().isNotFound());
	}

	@Test
	public void testAmendTicket() throws Exception {
		Optional<Ticket> fetchedTicket = produceTicket();
		Ticket amendedTicket = produceAmendedTicket();
		when(ticketService.getTicketById(1L)).thenReturn(fetchedTicket);
		when(ticketService.amendTicketLines(fetchedTicket.get(), 2)).thenReturn(amendedTicket);
		String expected = "{\"id\":1,\"ticketLines\":[{\"numbers\":[1,0,1]},{\"numbers\":[0,0,0]}],\"checked\":false}";
		String actual = mockMvc.perform(put("/lottery/ticket/1?lineNumber=2")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		assertEquals(expected, actual);
	}

	@Test
	public void testAmendTicketTicketNotFoundException() throws Exception {
		mockMvc.perform(put("/lottery/ticket/1?lineNumber=2")).andExpect(status().isNotFound());
	}

	@Test
	public void testAmendTicketCheckedException() throws Exception {
		Optional<Ticket> fetchedTicket = produceTicket();
		fetchedTicket.get().setChecked(true);
		when(ticketService.getTicketById(1L)).thenReturn(fetchedTicket);
		mockMvc.perform(put("/lottery/ticket/1?lineNumber=2")).andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testCheckTicket() throws Exception {
		Optional<Ticket> fetchedTicket = produceTicket();

		when(ticketService.getTicketById(1L)).thenReturn(fetchedTicket);
		when(ticketService.checkTicket(fetchedTicket.get())).thenReturn(produceTicketStatus(fetchedTicket.get()));
		String expected = "{\"ticketId\":1,\"resultList\":[{\"numbers\":[1,0,1],\"result\":0},{\"numbers\":[0,0,0],\"result\":0},{\"numbers\":[2,0,1],\"result\":0},{\"numbers\":[2,2,1],\"result\":0}],\"checked\":true}";
		String actual = mockMvc.perform(put("/lottery/status/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		assertEquals(expected, actual);

	}

	@Test
	public void testCheckTicketTicketNotFoundException() throws Exception {
		mockMvc.perform(put("/lottery/status/1")).andExpect(status().isNotFound());
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
		return new TicketStatus(ticket);
	}

}
