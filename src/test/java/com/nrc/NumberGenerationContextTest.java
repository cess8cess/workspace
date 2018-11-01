package com.nrc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nrc.context.NumberGenerationContext;
import com.nrc.strategy.StandartNumberGenerationStrategy;

@ContextConfiguration(classes = { LotteryApplication.class }, initializers = {
		ConfigFileApplicationContextInitializer.class })
@RunWith(SpringRunner.class)
public class NumberGenerationContextTest {

	@Autowired
	NumberGenerationContext context;
	
	@Test
	public void testStandartGenerationNumbers() {
		context.setGenerationStrategy(new StandartNumberGenerationStrategy());
		int[] numbers = context.generate();
		assertTrue(numbers.length > 0 );
		for (int i : numbers) {
			assertTrue(i < StandartNumberGenerationStrategy.NUMBER_COUNT);
		}
	}

}
