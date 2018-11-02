package com.nrc.strategy;

import java.util.Arrays;
import java.util.Random;

public class StandartNumberGenerationStrategy implements INumberGenerationStrategy {

	@Override
	public int[] generate() {
		int[] numbers = new int[NUMBER_COUNT];
		// fills the array with generated random values
		Arrays.parallelSetAll(numbers, num -> new Random().nextInt(NUMBER_COUNT));
		return numbers;
	}

}
