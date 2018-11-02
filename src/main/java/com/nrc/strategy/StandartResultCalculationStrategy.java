package com.nrc.strategy;

import java.util.Arrays;
import java.util.function.IntPredicate;

import org.springframework.stereotype.Component;

@Component
public class StandartResultCalculationStrategy implements IResultCalculationStrategy {

	@Override
	public int calculate(int[] numbers) {
		IntPredicate p = num -> num == numbers[0];

		if (Arrays.stream(numbers).sum() == RESULT_10) {
			/** sum of the array elements equals 2 */
			return 10;
		} else if (Arrays.stream(numbers).allMatch(p)) {
			/** all elements of the array are equal */
			return 5;
		} else if (Arrays.stream(numbers).skip(1).noneMatch(p)) {
			/** elements are different from 1st element */
			return 1;
		}
		return 0;
	}

}
