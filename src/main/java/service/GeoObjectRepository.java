package service;

import model.Aerodrome;
import model.GeoObject;

import java.util.Random;

public class GeoObjectRepository {
	private Logger logger;
	private Random random;
	private RandomSleeper randomSleeper;
	private Aerodrome[] collection;

	public GeoObjectRepository(final Logger logger, final Random random, final RandomSleeper randomSleeper) {
		this.logger = logger;
		this.random = random;
		this.randomSleeper = randomSleeper;
		initCollection();
	}

	public GeoObject getRandom() {
		return this.collection[this.random.nextInt(this.collection.length)];
	}

	public void updateCoordinates(final GeoObject geoObject, final String latitude, final String longitude) {
		this.logger.log("Saving " + geoObject.getName() + " coordinates in DB to " + latitude + ", " + longitude);
		this.randomSleeper.sleepLong();
		this.logger.log("Saved " + geoObject.getName() + " coordinates in DB to " + latitude + ", " + longitude);
	}

	public void printAll() {
		for (Aerodrome aerodrome : this.collection) {
			this.logger.log(aerodrome.toString());
		}
	}

	private void initCollection() {
		this.collection = new Aerodrome[]{
				new Aerodrome("Bratislava", "48.17N", "17.19E"),
				new Aerodrome("Praha", "50.55N", "14.17E"),
				new Aerodrome("NY", "40.64N", "73.77W"),
				new Aerodrome("London", "51.46", "0.45W"),
				new Aerodrome("Budapest", "47.43N", "19.25E"),
				new Aerodrome("Sydney", "33.94S", "151.17E"),
				new Aerodrome("Tokyo", "35.77N", "140.38E"),
				new Aerodrome("Moscow", "55.41N", "37.90E"),
				new Aerodrome("Buenos Aires", "34.82S", "58.53W"),
				new Aerodrome("Cape Town", "33.97S", "18.60E")
		};
	}
}
