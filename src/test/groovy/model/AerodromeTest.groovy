package model

import spock.lang.Specification

class AerodromeTest extends Specification {
    Aerodrome aerodrome

    void setup() {
        this.aerodrome = new Aerodrome("Bratislava", "48.17N", "17.19E")
    }

    def "ToString generates a nice text"() {
        expect:
        this.aerodrome.toString() == "Aerodrome(Bratislava, 48.17N, 17.19E)"
    }
}
