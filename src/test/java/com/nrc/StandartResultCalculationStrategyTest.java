package com.nrc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.nrc.model.TicketLine;
import com.nrc.strategy.ResultCalculationStrategy;

@RunWith(SpringRunner.class)
public class StandartResultCalculationStrategyTest {

	@Test
	public void testCalculateResults10() {
		int[][] arr = { new int[] { 0, 1, 1 }, new int[] { 1, 0, 1 }, new int[] { 1, 1, 0 }, new int[] { 2, 0, 0 },
				new int[] { 0, 2, 0 }, new int[] { 0, 0, 2 } };

		for (int[] nums : arr) {
			assertEquals(10, ResultCalculationStrategy.standartCalculate(new TicketLine(nums)).getResult());
		}
	}

	@Test
	public void testCalculateResults5() {
		int[][] arr = { new int[] { 0, 0, 0 }, new int[] { 1, 1, 1 }, new int[] { 2, 2, 2 }, };

		for (int[] nums : arr) {
			assertEquals(5, ResultCalculationStrategy.standartCalculate(new TicketLine(nums)).getResult());
		}
	}

	@Test
	public void testCalculateResults1() {
		int[][] arr = { new int[] { 0, 1, 2 }, new int[] { 0, 2, 1 }, new int[] { 1, 0, 0 }, new int[] { 1, 2, 0 },
				new int[] { 1, 0, 2 }, new int[] { 2, 1, 0 }, new int[] { 2, 0, 1 }, new int[] { 2, 1, 1 },
				new int[] { 0, 2, 2 }, new int[] { 1, 2, 2 }, };

		for (int[] nums : arr) {
			assertEquals(1, ResultCalculationStrategy.standartCalculate(new TicketLine(nums)).getResult());
		}
	}

	@Test
	public void testCalculateResults0() {
		int[][] arr = { new int[] { 0, 0, 1 }, new int[] { 0, 1, 0 }, new int[] { 1, 1, 2 }, new int[] { 1, 2, 1 },
				new int[] { 2, 1, 2 }, new int[] { 2, 2, 1 } };

		for (int[] nums : arr) {
			assertEquals(0, ResultCalculationStrategy.standartCalculate(new TicketLine(nums)).getResult());
		}
	}

	@Test
	public void testResultComparison() {
		List<TicketLine> lines = new ArrayList<>();
		TicketLine line = new TicketLine();
		line.setResult(5);
		lines.add(line);
		line = new TicketLine();
		line.setResult(0);
		lines.add(line);
		line = new TicketLine();
		line.setResult(10);
		lines.add(line);
		line = new TicketLine();
		line.setResult(1);
		lines.add(line);

		Collections.sort(lines, ResultCalculationStrategy::compareResultGreaterFirst);

		String resultPrint = lines.stream().map(l -> String.valueOf(l.getResult())).reduce("", (a, b) -> a + "," + b);
		assertEquals(",10,5,1,0", resultPrint);

	}

}
