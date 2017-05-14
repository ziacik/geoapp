package service

import model.GeoObject
import spock.lang.Specification
import spock.lang.Timeout
import util.PersistCommand
import util.PersistCommandHandler
import util.PersistCommandHandlerFactory

@Timeout(2)
class PersistCommandBusTest extends Specification {
    PersistCommandBus persistCommandBus
    Logger logger
    ConfigurationService configurationService
    AppStateService appStateService
    PersistCommandHandlerFactory persistCommandHandlerFactory

    void setup() {
        this.logger = Mock(Logger)
        this.configurationService = Stub(ConfigurationService) {
            getNumberOfPersistThreads() >> 2
        }
        this.appStateService = Stub(AppStateService)
        this.persistCommandHandlerFactory = Mock(PersistCommandHandlerFactory)
        this.persistCommandBus = new PersistCommandBus(this.configurationService, this.appStateService, this.persistCommandHandlerFactory, this.logger)
    }

    def "Start creates N command handlers and runs them"() {
        given:
        def handler1 = Mock(PersistCommandHandler, constructorArgs: [null])
        def handler2 = Mock(PersistCommandHandler, constructorArgs: [null])

        when:
        this.persistCommandBus.start()
        sleep(500)

        then:
        2 * this.persistCommandHandlerFactory.create(this.persistCommandBus) >> handler1 >> handler2

        and:
        1 * handler1.run()
        1 * handler2.run()
    }

    def "Commands with different keys can be enqueued and dequeued in correct order"() {
        given:
        def command1 = Stub(PersistCommand) {
            getKey() >> "Key1"
        }
        def command2 = Stub(PersistCommand) {
            getKey() >> "Key2"
        }

        when:
        this.persistCommandBus.enqueue(command1)
        this.persistCommandBus.enqueue(command2)
        def result1 = this.persistCommandBus.dequeue()
        def result2 = this.persistCommandBus.dequeue()

        then:
        result1 == command1
        result2 == command2
    }

    def "Command with the same key can be dequeued after previous one signaled its completion with done"() {
        given:
        def command1 = Stub(PersistCommand) {
            getKey() >> "Key"
        }
        def command2 = Stub(PersistCommand) {
            getKey() >> "Key"
        }

        when:
        this.persistCommandBus.enqueue(command1)
        this.persistCommandBus.enqueue(command2)
        def result1 = this.persistCommandBus.dequeue()
        this.persistCommandBus.done(command1)
        def result2 = this.persistCommandBus.dequeue()

        then:
        result1 == command1
        result2 == command2
    }

    def "When the command is dequeued, it is logged"() {
        given:
        def command = Stub(PersistCommand) {
            getKey() >> "Key"
        }

        when:
        this.persistCommandBus.enqueue(command)
        this.persistCommandBus.dequeue()

        then:
        1 * this.logger.log({ it.endsWith("Processing change " + command) })
    }
}
