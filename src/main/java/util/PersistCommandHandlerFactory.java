package util;

import service.AppStateService;
import service.PersistCommandBus;

public class PersistCommandHandlerFactory {
	public PersistCommandHandler create(final PersistCommandBus persistCommandBus) {
		return new PersistCommandHandler(persistCommandBus);
	}
}
