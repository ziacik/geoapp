package util;

public interface PersistCommand {
	String getKey();
	void execute();
}
