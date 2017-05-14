package util

import service.AppStateService
import service.Logger
import service.PersistCommandBus
import spock.lang.Specification

class PersistCommandHandlerTest extends Specification {
    PersistCommandHandler handler
    PersistCommandBus commandBus

    void setup() {
        this.commandBus = Mock(PersistCommandBus, constructorArgs: [null, null, null, null])
        this.handler = new PersistCommandHandler(this.commandBus)
    }

    def "Run dequeues commands, executes them and signals that its done"() {
        given:
        def command1 = Mock(PersistCommand)
        def command2 = Mock(PersistCommand)

        when:
        this.handler.run()

        then:
        3 * this.commandBus.dequeue() >> command1 >> command2 >> null

        and:
        1 * command1.execute()
        1 * command2.execute()

        and:
        1 * commandBus.done(command1)
        1 * commandBus.done(command2)
    }
}
