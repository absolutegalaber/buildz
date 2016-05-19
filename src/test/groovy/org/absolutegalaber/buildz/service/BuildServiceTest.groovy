package org.absolutegalaber.buildz.service

import org.absolutegalaber.buildz.BaseBuildzSpec
import org.absolutegalaber.buildz.domain.Build
import spock.lang.Subject

import javax.inject.Inject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildServiceTest extends BaseBuildzSpec {
    @Subject
    @Inject
    BuildService service

    def "ById"() {
        when:
        Optional<Build> build = service.byId(id)

        then:
        build.isPresent() == expected

        where:
        id  | expected | message
        1L  | true     | 'ById finds existing Build'
        -1L | false    | 'ById is empty for missing Build'

    }

    def "Create"() {
        expect:
        service.create('buildz-project', 'next', 1).id
    }

    def "AddLabels"() {
        //TODO: Test Me
    }

    def "Stats"() {
        expect:
        service.stats().numberOfBuilds == 5L
    }

    def "Search"() {
        //TODO: Test Me
    }
}
