package util

import spock.lang.Specification

class PersistCommandHandlerFactoryTest extends Specification {
    PersistCommandHandlerFactory factory

    void setup() {
        this.factory = new PersistCommandHandlerFactory()
    }

    def "Can create a handler"() {
        when:
        def handler = this.factory.create(null)

        then:
        handler instanceof PersistCommandHandler
    }
}
