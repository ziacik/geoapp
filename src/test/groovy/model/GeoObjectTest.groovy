package model

import spock.lang.Specification

class GeoObjectTest extends Specification {
    GeoObject geoObject

    void setup() {
        this.geoObject = Spy(GeoObject, constructorArgs:["Bratislava", "48.17N", "17.19E"])
    }

    def "Can have a location changed"() {
        when:
        this.geoObject.changeLocation("40.00S", "12.12W")

        then:
        this.geoObject.latitude == "40.00S"
        this.geoObject.longitude == "12.12W"
    }
}
