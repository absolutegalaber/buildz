package org.absolutegalaber.buildz.service

import org.absolutegalaber.buildz.BaseBuildzSpec
import org.absolutegalaber.buildz.domain.BuildStats
import spock.lang.Subject

import javax.inject.Inject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class StatsServiceTest extends BaseBuildzSpec {

    @Subject
    @Inject
    StatsService service;

    def "Stats"() {
        when:
        BuildStats stats = service.stats()

        then:
        stats.numberOfBuilds > 0

        and:
        stats.projects.size() > 0

        and:
        stats.environments.size() > 0
    }
}
