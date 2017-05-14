package service;

import java.util.Random;

public class RandomChangeGenerator {
	private Random random;

	public RandomChangeGenerator(final Random random) {
		this.random = random;
	}

	public String getRandomLatitude() {
		return getRandomDegree() + (this.random.nextBoolean() ? "S" : "N");
	}

	public String getRandomLongitude() {
		return getRandomDegree() + (this.random.nextBoolean() ? "E" : "W");
	}

	private String getRandomDegree() {
		final int num = this.random.nextInt(9000);
		return String.format("%02d.%02d", num / 100, num % 100);
	}
}
