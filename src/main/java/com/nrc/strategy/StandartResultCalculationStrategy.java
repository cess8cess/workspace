package com.nrc.strategy;

import java.util.Arrays;
import java.util.function.IntPredicate;


import com.nrc.model.TicketLine;

public interface StandartResultCalculationStrategy {

	/** 
	 * sum of the array values equals 2 then result is 10 <br> 
	 * array values are all the same then result is 5 <br> 
	 * 2nd and 3rd elements are different than 1st element then result is 1 <br>
	 * otherwise result is 0 <br>
	 *
	 * @param line TicketLine
	 * @return TicketLine
	 * 
	 * @numbers [0,1,1] -> 10 <br>
	 * @numbers [1,1,1] -> 5 <br>
	 * @numbers [2,1,0] -> 1 <br> 
	 * @numbers [1,1,2] -> 0 <br>
	 * */
	 static TicketLine calculate(TicketLine line) {
		int[] numbers = line.getNumbers();
		IntPredicate p = num -> num == numbers[0];
		int result = 0;

		if (Arrays.stream(numbers).sum() == 2) {
			/** sum of the elements equals 2 */
			result = 10;
		} else if (Arrays.stream(numbers).allMatch(p)) {
			/** all elements are equal */
			result = 5;
		} else if (Arrays.stream(numbers).skip(1).noneMatch(p)) {
			/** elements except 1st are different from 1st element */
			result = 1;
		}

		line.setResult(result);
		return line;
	}
	
	/**
	 * compare ticket lines according to result value,
	 * result sorting 10, 5, 1, 0
	 * */
	 static int compare(TicketLine t1, TicketLine t2) {
		return t2.getResult() - t1.getResult();
	}

}
