package service;

public class ConfigurationService {
	private int numberOfChanges = 100;
	private int numberOfPersistThreads = 10;

	public ConfigurationService(final String[] args) {
		initFromArgs(args);
	}

	public int getNumberOfChanges() {
		return this.numberOfChanges;
	}

	public int getNumberOfPersistThreads() {
		return this.numberOfPersistThreads;
	}

	private void initFromArgs(String[] args) {
		for (int i = 0; i < args.length - 1; i++) {
			if ("--changes".equals(args[i])) {
				i++;
				this.numberOfChanges = Integer.parseInt(args[i]);
			} else if ("--threads".equals(args[i])) {
				i++;
				this.numberOfPersistThreads = Integer.parseInt(args[i]);
			}
		}
	}
}
