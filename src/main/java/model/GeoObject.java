package model;

public abstract class GeoObject {
	private final String name;
	private String latitude;
	private String longitude;

	public GeoObject(final String name, final String latitude, final String longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void changeLocation(final String newLatitude, final String newLongitude) {
		this.latitude = newLatitude;
		this.longitude = newLongitude;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + getName() + ", " + getLatitude() + ", " + getLongitude() + ")";
	}
}
