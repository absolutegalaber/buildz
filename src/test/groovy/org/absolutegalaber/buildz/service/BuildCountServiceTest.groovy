package org.absolutegalaber.buildz.service

import org.absolutegalaber.buildz.BaseBuildzSpec
import org.absolutegalaber.buildz.domain.BuildCount
import spock.lang.Subject
import spock.lang.Unroll

import javax.inject.Inject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildCountServiceTest extends BaseBuildzSpec {
    @Subject
    @Inject
    BuildCountService service

    @Unroll("#message")
    def "Next"() {
        when:
        BuildCount buildCount = service.next(project, branch)

        then:
        buildCount.count == expected

        and:
        buildCount.project == project

        and:
        buildCount.branch == branch

        where:
        project             | branch    | expected | message
        'buildz-backend'    | 'master'  | 2L       | "Next(): Increments existing build count"
        'buildz-backend'    | 'release' | 1L       | "Next(): Creates non-existing build count for new branch"
        'buildz-sub-module' | 'master'  | 1L       | "Next(): Creates non-existing build count for new project"

    }

    def "Current"() {
        when:
        BuildCount buildCount = service.current(project, branch)

        then:
        buildCount.count == expected

        and:
        buildCount.project == project

        and:
        buildCount.branch == branch

        where:
        project          | branch    | expected | message
        'buildz-backend' | 'master'  | 1L       | "Current(): returns current existing build count"
        'buildz-backend' | 'release' | 0L       | "Current(): return non-existing build count with count = 0"
    }

    def "Set"() {
        when:
        service.set(project, branch, 10L)
        BuildCount buildCount = service.current(project, branch)

        then:
        buildCount.count == 10L

        where:
        project             | branch    | message
        'buildz-backend'    | 'master'  | "Set(): sets build number for existing build count"
        'buildz-sub-module' | 'release' | "Set(): creates build number for existing build count"
    }
}
