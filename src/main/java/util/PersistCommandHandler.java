package util;

import service.AppStateService;
import service.PersistCommandBus;

public class PersistCommandHandler implements Runnable {
	private PersistCommandBus persistCommandBus;

	public PersistCommandHandler(final PersistCommandBus persistCommandBus) {
		this.persistCommandBus = persistCommandBus;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			final PersistCommand command = dequeueCommand();

			if (command == null) {
				break;
			}

			command.execute();
			this.persistCommandBus.done(command);
		}
	}

	private PersistCommand dequeueCommand() {
		try {
			return this.persistCommandBus.dequeue();
		} catch (final InterruptedException e) {
			return null;
		}
	}
}
