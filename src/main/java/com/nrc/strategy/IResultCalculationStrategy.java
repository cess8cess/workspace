package com.nrc.strategy;

/** The Interface IResultCalculationStrategy. */
public interface IResultCalculationStrategy {
	final static int RESULT_10 = 2;

	/** calculate <p> sum of the array values equals 2 then return 10 <br> array
	 * values are all the same then return 5 <br> 2nd and 3rd elements are different
	 * than 1st element then return 1 <br> otherwise return 0 <br>
	 *
	 * @param numbers the numbers
	 * @return the calculated result
	 * 
	 *         <p> inputs and returns <br> @input[0,1,1] -> 10 <br> @input[1,1,1] ->
	 *         5 <br> @input[2,1,0] -> 1 <br> @input[1,1,2] -> 0 */

	public int calculate(int[] numbers);
}
