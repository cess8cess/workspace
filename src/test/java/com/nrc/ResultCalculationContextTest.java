package com.nrc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nrc.context.ResultCalculationContext;
import com.nrc.model.Ticket;
import com.nrc.model.TicketLine;
import com.nrc.model.TicketStatus;
import com.nrc.service.ITicketService;
import com.nrc.strategy.StandartResultCalculationStrategy;

@ContextConfiguration(classes = { LotteryApplication.class }, initializers = {
		ConfigFileApplicationContextInitializer.class })
@RunWith(SpringRunner.class)
public class ResultCalculationContextTest {

	@Autowired
	ResultCalculationContext context;

	@Autowired
	ITicketService ticketService;

	@Test
	public void testStandartResultCalculationForResult10() {
		context.setCalculationStrategy(new StandartResultCalculationStrategy());

		int[][] arr = { new int[] { 0, 1, 1 }, new int[] { 1, 0, 1 }, new int[] { 1, 1, 0 }, new int[] { 2, 0, 0 },
				new int[] { 0, 2, 0 }, new int[] { 0, 0, 2 } };

		for (int[] is : arr) {
			assertEquals(10, context.calculateResult(is));
		}

	}

	@Test
	public void testStandartResultCalculationForResult5() {
		context.setCalculationStrategy(new StandartResultCalculationStrategy());

		int[][] arr = { new int[] { 0, 0, 0 }, new int[] { 1, 1, 1 }, new int[] { 2, 2, 2 }, };

		for (int[] is : arr) {
			assertEquals(5, context.calculateResult(is));
		}

	}

	@Test
	public void testStandartResultCalculationForResult1() {
		context.setCalculationStrategy(new StandartResultCalculationStrategy());

		int[][] arr = { new int[] { 0, 1, 2 }, new int[] { 0, 2, 1 }, new int[] { 1, 0, 0 }, new int[] { 1, 2, 0 },
				new int[] { 1, 0, 2 }, new int[] { 2, 1, 0 }, new int[] { 2, 0, 1 }, new int[] { 2, 1, 1 },
				new int[] { 0, 2, 2 }, new int[] { 1, 2, 2 }, };

		for (int[] is : arr) {
			assertEquals(1, context.calculateResult(is));
		}

	}

	@Test
	public void testStandartResultCalculationForResult0() {
		context.setCalculationStrategy(new StandartResultCalculationStrategy());

		int[][] arr = { new int[] { 0, 0, 1 }, new int[] { 0, 1, 0 }, new int[] { 1, 1, 2 }, new int[] { 1, 2, 1 },
				new int[] { 2, 1, 2 }, new int[] { 2, 2, 1 } };

		for (int[] is : arr) {
			assertEquals(0, context.calculateResult(is));
		}

	}

	@Test
	public void TestLinesResultSorting() {
		Ticket ticket = produceTicket();
		TicketStatus ticketStatus = ticketService.checkTicket(ticket);
		assertEquals(ticketStatus.getResultList().get(0).getResult(), 10);
		assertEquals(ticketStatus.getResultList().get(1).getResult(), 5);
		assertEquals(ticketStatus.getResultList().get(2).getResult(), 1);
		assertEquals(ticketStatus.getResultList().get(3).getResult(), 0);
	}

	private Ticket produceTicket() {
		int[] numbers10 = { 1, 1, 0 };
		int[] numbers5 = { 1, 1, 1 };
		int[] numbers1 = { 2, 1, 0 };
		int[] numbers0 = { 2, 1, 2 };

		List<TicketLine> ticketLines = new ArrayList<>();
		ticketLines.add(new TicketLine(numbers10));
		ticketLines.add(new TicketLine(numbers5));
		ticketLines.add(new TicketLine(numbers1));
		ticketLines.add(new TicketLine(numbers0));
		Ticket ticket = new Ticket(ticketLines);
		ticket.setChecked(false);
		return ticket;
	}

}
