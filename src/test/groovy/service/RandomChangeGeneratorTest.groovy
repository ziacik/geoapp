package service

import spock.lang.Specification

class RandomChangeGeneratorTest extends Specification {
    RandomChangeGenerator randomChangeGenerator
    Random random

    void setup() {
        this.random = Stub(Random)
        this.randomChangeGenerator = new RandomChangeGenerator(this.random)
    }

    def "GetRandomLatitude creates a random latitude"() {
        given:
        this.random.nextInt(9000) >> 1234 >> 2300
        this.random.nextBoolean() >> true >> false

        expect:
        this.randomChangeGenerator.getRandomLatitude() == "12.34S"
        this.randomChangeGenerator.getRandomLatitude() == "23.00N"
    }

    def "GetRandomLongitude creates a random longitude"() {
        given:
        this.random.nextInt(9000) >> 8999 >> 12
        this.random.nextBoolean() >> false >> true

        expect:
        this.randomChangeGenerator.getRandomLongitude() == "89.99W"
        this.randomChangeGenerator.getRandomLongitude() == "00.12E"
    }
}
