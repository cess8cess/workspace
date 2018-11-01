package com.nrc.strategy;

public interface INumberGenerationStrategy {
	public final static int NUMBER_COUNT = 3; // numbers will be generated in each line
	
	
	/**
	 * Generates a pseudorandom, 
	 * uniformly distributed int[] array value 
	 * between 0 (inclusive) and the specified value (exclusive)
	 *
	 * @return the int[]
	 */
	
	public int[] generate();
}
