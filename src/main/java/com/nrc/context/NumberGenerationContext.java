package com.nrc.context;

import org.springframework.stereotype.Component;

import com.nrc.strategy.INumberGenerationStrategy;

/** The Class NumberGenerationContext. */
@Component
public class NumberGenerationContext {
	private INumberGenerationStrategy strategy;

	/** Sets the number generation strategy.
	 *
	 * @param strategy the new calculation strategy */
	public void setGenerationStrategy(INumberGenerationStrategy strategy) {
		this.strategy = strategy;
	}

	/** number generation with the provided strategy
	 *
	 * @return the int[] */
	public int[] generate() {
		return strategy.generate();
	}
}
