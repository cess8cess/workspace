package com.nrc.context;

import org.springframework.stereotype.Component;

import com.nrc.strategy.IResultCalculationStrategy;

/**
 * The Class ResultCalculationContext.
 */
@Component
public class ResultCalculationContext {
	private IResultCalculationStrategy strategy;

	
	/**
	 * Sets the calculation strategy.
	 *
	 * @param strategy the new calculation strategy
	 */
	public void setCalculationStrategy(IResultCalculationStrategy strategy) {
		this.strategy = strategy;
	}

	
	/**
	 * Calculate result with the provided strategy
	 *
	 * @param numbers int[]
	 * @return result int
	 */
	public int calculateResult(int[] numbers) {
		return strategy.calculate(numbers);
	}
}
