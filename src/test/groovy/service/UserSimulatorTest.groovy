package service

import model.Aerodrome
import model.GeoObject
import spock.lang.Specification
import util.GeoObjectPersistCommand
import util.PersistCommand

class UserSimulatorTest extends Specification {
    UserSimulator simulator
    ConfigurationService configurationService
    AppStateService appStateService
    GeoObjectRepository repository
    PersistCommandBus persistCommandBus
    RandomChangeGenerator randomChangeGenerator
    RandomSleeper randomSleeper
    Logger logger

    void setup() {
        this.configurationService = Stub(ConfigurationService) {
            getNumberOfChanges() >> 2
        }
        this.appStateService = new AppStateService()
        this.randomSleeper = Mock(RandomSleeper)
        this.logger = Mock(Logger)
        this.persistCommandBus = Mock(PersistCommandBus, constructorArgs: [null, null, null, null])
        this.repository = Mock(GeoObjectRepository, constructorArgs:[null, Stub(Random), null])
        this.randomChangeGenerator = Mock(RandomChangeGenerator)
        this.simulator = new UserSimulator(this.configurationService, this.appStateService, this.repository, this.persistCommandBus, this.randomSleeper, this.randomChangeGenerator, this.logger)
    }

    def "MakeRandomChanges makes N changes to random objects with short sleeps between, logs the changes and enqueues persist commands for them"() {
        given:
        def geoObject1 = new Aerodrome("Something", "12N", "13E")
        def geoObject2 = new Aerodrome("Another", "12N", "13E")

        when:
        this.simulator.makeRandomChanges()

        then:
        2 * this.repository.getRandom() >> geoObject1 >> geoObject2

        and:
        2 * this.randomChangeGenerator.getRandomLongitude() >> "Lon1" >> "Lon2"
        2 * this.randomChangeGenerator.getRandomLatitude() >> "Lat1" >> "Lat2"

        and:
        1 * this.logger.log({ it.contains("Something") && it.contains("Lon1") && it.contains("Lat1") })
        1 * this.logger.log({ it.contains("Another") && it.contains("Lon2") && it.contains("Lat2") })

        and:
        1 * this.persistCommandBus.enqueue({ it.latitude == "Lat1" && it.longitude == "Lon1"})
        1 * this.persistCommandBus.enqueue({ it.latitude == "Lat2" && it.longitude == "Lon2"})

        and:
        2 * this.randomSleeper.sleepShort()

        then:
        geoObject1.latitude == "Lat1"
        geoObject1.longitude == "Lon1"
        geoObject2.latitude == "Lat2"
        geoObject2.longitude == "Lon2"
    }

    def "Requests app finish after making all the changes"() {
        given:
        2 * this.repository.getRandom() >> Stub(GeoObject, constructorArgs: [null, null, null])

        when:
        this.simulator.makeRandomChanges()

        then:
        this.appStateService.isFinishPending()
    }
}
