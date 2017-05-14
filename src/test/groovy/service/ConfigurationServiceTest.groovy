package service

import spock.lang.Specification

class ConfigurationServiceTest extends Specification {
    ConfigurationService service

    void setup() {
        this.service = new ConfigurationService([] as String[])
    }

    def "GetNumberOfChanges returns 100 by default"() {
        expect:
        this.service.getNumberOfChanges() == 100
    }

    def "GetNumberOfPersistThreads returns 10 by default"() {
        expect:
        this.service.getNumberOfPersistThreads() == 10
    }

    def "GetNumberOfChanges returns value of changes parameter if specified"() {
        given:
        this.service = new ConfigurationService(["--whatever", "--changes", "123"] as String[])

        expect:
        this.service.getNumberOfChanges() == 123
    }

    def "GetNumberOfPersistThreads returns value of threads parameter if specified"() {
        given:
        this.service = new ConfigurationService(["--threads", "42"] as String[])

        expect:
        this.service.getNumberOfPersistThreads() == 42
    }
}
