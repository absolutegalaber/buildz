package org.absolutegalaber.buildz.service

import org.absolutegalaber.buildz.BaseBuildzSpec
import org.absolutegalaber.buildz.domain.BuildStats
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class StatsServiceTest extends BaseBuildzSpec {

    @Subject
    @Autowired
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
