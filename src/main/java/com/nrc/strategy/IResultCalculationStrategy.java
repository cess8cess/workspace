package com.nrc.strategy;

/**
 * The Interface IResultCalculationStrategy.
 */
public interface IResultCalculationStrategy {
	final static int RESULT_10 = 2;
	
	
	/**
	 * calculate
	 * sum of the array values equals 2 then return 10
	 * array values are all the same then return 5
	 * 2nd and 3rd elements are different than 1st element then return 1
	 * otherwise return 0
	 *
	 * @param numbers the numbers
	 * @return the calculated result
	 * 
	 * @sample inputs and returns
	 * @input[0,1,1] -> 10 
	 * @input[1,1,1] -> 5
	 * @input[2,1,0] -> 1
	 * @input[1,1,2] -> 0
	 * 
	 */
	
	public int calculate(int[] numbers);
}
