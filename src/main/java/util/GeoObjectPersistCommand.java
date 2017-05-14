package util;

import model.GeoObject;
import service.GeoObjectRepository;

public class GeoObjectPersistCommand implements PersistCommand {
	private final GeoObjectRepository repository;
	private final GeoObject geoObject;
	private final String latitude;
	private final String longitude;

	public GeoObjectPersistCommand(final GeoObjectRepository repository, final GeoObject geoObject, final String latitude, final String longitude) {
		this.repository = repository;
		this.geoObject = geoObject;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String getKey() {
		return this.geoObject.getName();
	}

	@Override
	public void execute() {
		this.repository.updateCoordinates(this.geoObject, this.latitude, this.longitude);
	}

	@Override
	public String toString() {
		return this.geoObject.getName() + " -> " + this.latitude + ", " + this.longitude;
	}
}
