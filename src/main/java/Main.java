import service.*;
import util.PersistCommandHandlerFactory;

import java.util.Random;

public class Main {
	public static void main(final String[] args) {
		final ConfigurationService configurationService = new ConfigurationService(args);
		final Logger logger = new Logger();
		final Random random = new Random();
		final RandomSleeper randomSleeper = new RandomSleeper();
		final RandomChangeGenerator randomChangeGenerator = new RandomChangeGenerator(random);
		final GeoObjectRepository repository = new GeoObjectRepository(logger, random, randomSleeper);
		final AppStateService appStateService = new AppStateService();
		final PersistCommandHandlerFactory persistCommandHandlerFactory = new PersistCommandHandlerFactory();
		final PersistCommandBus persistCommandBus = new
				PersistCommandBus(configurationService, appStateService,
				persistCommandHandlerFactory, logger);
		final UserSimulator userSimulator = new UserSimulator(configurationService, appStateService, repository, persistCommandBus, randomSleeper, randomChangeGenerator, logger);
		persistCommandBus.start();
		userSimulator.makeRandomChanges();
		persistCommandBus.shutdown();
		repository.printAll();
	}
}
