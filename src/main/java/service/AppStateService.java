package service;

public class AppStateService {
	private boolean isFinishPending = false;

	public void requestFinish() {
		this.isFinishPending = true;
	}

	public boolean isFinishPending() {
		return this.isFinishPending;
	}
}
