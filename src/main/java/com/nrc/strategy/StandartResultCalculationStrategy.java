package com.nrc.strategy;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;


@Component
public class StandartResultCalculationStrategy implements IResultCalculationStrategy {

	@Override
	public int calculate(int[] numbers) {
		IntPredicate p1 = value -> value == numbers[0];

		/** sum of the array elements equals 2 */
		if (Arrays.stream(numbers).sum() == RESULT_10) {
			return 10;
		} else if (Arrays.stream(numbers).allMatch(number -> number == numbers[0])) { /** all elements of the array are equal */
			return 5;
		} else if (Arrays.stream(IntStream.range(1, numbers.length).map(i -> numbers[i]).toArray())
				.noneMatch(p1)) { /** elements are different from 1st element */
			return 1;
		}

		return 0;
	}

}
