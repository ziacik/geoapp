package util

import model.GeoObject
import service.GeoObjectRepository
import spock.lang.Specification

class GeoObjectPersistCommandTest extends Specification {
    GeoObjectPersistCommand command
    GeoObjectRepository repository
    GeoObject geoObject

    void setup() {
        this.repository = Mock(GeoObjectRepository, constructorArgs: [null, null, null])
        this.geoObject = Stub(GeoObject, constructorArgs:[null, null, null]) {
            getName() >> "geo object name"
        }
        this.command = new GeoObjectPersistCommand(this.repository, this.geoObject, "99N", "66E")
    }

    def "GetKey returns geo object's name"() {
        expect:
        this.command.getKey() == "geo object name"

    }

    def "Execute updates coordinates of the object via repository"() {
        when:
        this.command.execute()

        then:
        1 * this.repository.updateCoordinates(this.geoObject, "99N", "66E")
    }
}
