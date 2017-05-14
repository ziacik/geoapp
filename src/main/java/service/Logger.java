package service;

public class Logger {
	private final long startTime;

	public Logger() {
		this.startTime = System.currentTimeMillis();
	}

	public void log(final String message) {
		System.out.println(getTime() + " : " + message);
	}

	private String getTime() {
		long elapsedMillis = System.currentTimeMillis() - this.startTime;
		long elapsedSecs = elapsedMillis / 1000;
		return String.format("%d:%02d.%03d", elapsedSecs / 60, elapsedSecs % 60, elapsedMillis % 1000);
	}
}
