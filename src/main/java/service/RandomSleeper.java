package service;

import java.util.Random;

public class RandomSleeper {
	private Random random;

	public RandomSleeper() {
		this.random = new Random();
	}

	public void sleepLong() {
		final int randomTime = this.random.nextInt(2000) + 200;
		try {
			Thread.sleep(randomTime);
		} catch (final InterruptedException e) {
			System.err.println(e);
		}
	}

	public void sleepShort() {
		final int randomTime = this.random.nextInt(200) + 100;
		try {
			Thread.sleep(randomTime);
		} catch (final InterruptedException e) {
			System.err.println(e);
		}
	}
}
