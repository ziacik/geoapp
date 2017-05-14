package service

import spock.lang.Specification

class AppStateServiceTest extends Specification {
    AppStateService service

    void setup() {
        this.service = new AppStateService()
    }

    def "Finish is not pending by default"() {
        expect:
        !this.service.isFinishPending()
    }

    def "Finish is pending when requested"() {
        when:
        this.service.requestFinish()

        then:
        this.service.isFinishPending()
    }
}
