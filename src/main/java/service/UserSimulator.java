package service;

import model.GeoObject;
import util.GeoObjectPersistCommand;
import util.PersistCommand;

public class UserSimulator {
	private ConfigurationService configurationService;
	private AppStateService appStateService;
	private GeoObjectRepository repository;
	private PersistCommandBus persistCommandBus;
	private RandomSleeper randomSleeper;
	private RandomChangeGenerator randomChangeGenerator;
	private Logger logger;

	public UserSimulator(final ConfigurationService configurationService, final AppStateService appStateService, final GeoObjectRepository repository, final PersistCommandBus persistCommandBus, final RandomSleeper randomSleeper, final RandomChangeGenerator randomChangeGenerator, final Logger logger) {
		this.configurationService = configurationService;
		this.appStateService = appStateService;
		this.repository = repository;
		this.persistCommandBus = persistCommandBus;
		this.randomSleeper = randomSleeper;
		this.randomChangeGenerator = randomChangeGenerator;
		this.logger = logger;
	}

	public void makeRandomChanges() {
		for (int i = 0; i < this.configurationService.getNumberOfChanges(); i++) {
			final GeoObject geoObject = this.repository.getRandom();
			final String newLatitude = this.randomChangeGenerator.getRandomLatitude();
			final String newLongitude = this.randomChangeGenerator.getRandomLongitude();
			geoObject.changeLocation(newLatitude, newLongitude);
			this.logger.log("User changed the coordinates: " + geoObject);
			final PersistCommand command = new GeoObjectPersistCommand(this.repository, geoObject, newLatitude, newLongitude);
			this.persistCommandBus.enqueue(command);
			this.randomSleeper.sleepShort();
		}

		this.appStateService.requestFinish();
	}
}
