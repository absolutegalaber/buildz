package org.absolutegalaber.buildz.service

import org.absolutegalaber.buildz.BaseBuildzSpec
import org.absolutegalaber.buildz.domain.Environment
import org.absolutegalaber.buildz.domain.InvalidRequestException
import spock.lang.Subject
import spock.lang.Unroll

import javax.inject.Inject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class EnvironmentServiceTest extends BaseBuildzSpec {
    @Subject
    @Inject
    EnvironmentService service

    @Unroll("#message")
    def "ByName"() {
        when:
        Optional<Environment> env = service.byName(name)

        then:
        env.isPresent() == expected

        where:
        name                  | expected | message
        'master-test-stage-1' | true     | 'ByName(): finds existing Environment by name'
        'missing'             | false    | 'ByName(): is empty for non-existing environment name'


    }

    def "Create"() {
        expect:
        service.create("new-environment").id
    }

    def "Create with duplicate name"() {
        when:
        service.create("master-test-stage-1")

        then:
        thrown(InvalidRequestException)
    }
}
