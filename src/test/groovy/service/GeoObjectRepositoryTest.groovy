package service

import model.GeoObject
import spock.lang.Specification

class GeoObjectRepositoryTest extends Specification {
    GeoObjectRepository repository
    Random random
    Logger logger
    RandomSleeper randomSleeper

    void setup() {
        this.random = Stub(Random)
        this.logger = Mock(Logger)
        this.randomSleeper = Mock(RandomSleeper)
        this.repository = new GeoObjectRepository(this.logger, this.random, this.randomSleeper)
    }

    def "GetRandom returns the same geo object for the same random num"() {
        given:
        random.nextInt(_) >> 1

        when:
        def obj1 = this.repository.getRandom()
        def obj2 = this.repository.getRandom()

        then:
        obj1 != null
        obj1 == obj2
    }

    def "GetRandom returns different geo objects for different random nums"() {
        given:
        random.nextInt(_) >> 1 >> 2

        when:
        def obj1 = this.repository.getRandom()
        def obj2 = this.repository.getRandom()

        then:
        obj1 != null
        obj2 != null
        obj1 != obj2
    }

    def "UpdateCoordinates updates coordinates of geo object in DB"() {
        given:
        def obj = Spy(GeoObject, constructorArgs:["Something", "12N", "13E"])

        when:
        this.repository.updateCoordinates(obj, "22S", "33W")

        then:
        1 * this.logger.log({ it.endsWith("Saving Something coordinates in DB to 22S, 33W") })

        then:
        1 * this.randomSleeper.sleepLong()

        then:
        1 * this.logger.log({ it.endsWith("Saved Something coordinates in DB to 22S, 33W") })
    }

    def "PrintAll prints all objects"() {
        when:
        this.repository.printAll()

        then:
        10 * this.logger.log({ it.startsWith("Aerodrome") })
    }
}
