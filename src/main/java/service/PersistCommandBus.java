package service;

import model.GeoObject;
import util.GeoObjectPersistCommand;
import util.PersistCommand;
import util.PersistCommandHandler;
import util.PersistCommandHandlerFactory;

import java.util.*;
import java.util.concurrent.*;

public class PersistCommandBus {
	private List<PersistCommand> commandQueue;
	private Set<String> processingKeys;
	private ExecutorService threadPool;
	private ConfigurationService configurationService;
	private AppStateService appStateService;
	private PersistCommandHandlerFactory persistCommandHandlerFactory;
	private Logger logger;

	public PersistCommandBus(final ConfigurationService configurationService, final AppStateService appStateService, final PersistCommandHandlerFactory persistCommandHandlerFactory, final Logger logger) {
		this.configurationService = configurationService;
		this.appStateService = appStateService;
		this.persistCommandHandlerFactory = persistCommandHandlerFactory;
		this.logger = logger;
		this.commandQueue = new LinkedList<>();
		this.processingKeys = new HashSet<>();
	}

	public void start() {
		startExecutors();
	}

	public void shutdown() {
		try {
			this.threadPool.shutdown();
			this.threadPool.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
	}

	public synchronized void enqueue(final PersistCommand command) {
		this.commandQueue.add(command);
		notify();
	}

	public synchronized PersistCommand dequeue() throws InterruptedException {
		while(!hasCommand()) {
			if (this.appStateService.isFinishPending()) {
				notify();
				throw new InterruptedException();
			}

			wait();
		}

		final PersistCommand nextCommand = dequeueCommand();

		this.logger.log("Processing change " + nextCommand);

		notify();
		return nextCommand;
	}

	public synchronized void done(final PersistCommand command) {
		this.processingKeys.remove(command.getKey());
		notify();
	}

	private void startExecutors() {
		final int numberOfPersistThreads = this.configurationService.getNumberOfPersistThreads();
		this.threadPool = Executors.newFixedThreadPool(numberOfPersistThreads);
		for (int i = 0; i < numberOfPersistThreads; i++) {
			this.threadPool.execute(this.persistCommandHandlerFactory.create(this));
		}
	}

	private PersistCommand dequeueCommand() {
		for (final PersistCommand command : this.commandQueue) {
			if (!this.processingKeys.contains(command.getKey())) {
				this.processingKeys.add(command.getKey());
				this.commandQueue.remove(command);
				return command;
			}
		}

		throw new IllegalStateException();
	}

	private boolean hasCommand() {
		for (final PersistCommand command : this.commandQueue) {
			if (!this.processingKeys.contains(command.getKey())) {
				return true;
			}
		}
		return false;
	}
}
