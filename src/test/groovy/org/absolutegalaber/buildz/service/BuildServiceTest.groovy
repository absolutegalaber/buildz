package org.absolutegalaber.buildz.service

import org.absolutegalaber.buildz.BaseBuildzSpec
import org.absolutegalaber.buildz.domain.*
import spock.lang.Subject
import spock.lang.Unroll

import javax.inject.Inject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildServiceTest extends BaseBuildzSpec {
    @Subject
    @Inject
    BuildService service

    @Unroll("#message")
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
        service.create('buildz-backend', 'next', 1).id
    }

    def "AddLabels"() {
        given:
        BuildLabel label = new BuildLabel()
        label.setKey('newKey')
        label.setValue('newValue')

        when:
        Build theBuild = service.addLabels(1L, [label])

        then:
        theBuild.labels.size() == 3

    }

    def "AddLabels with wrong build id"() {
        given:
        BuildLabel label = new BuildLabel()
        label.setKey('newKey')
        label.setValue('newValue')

        when:
        service.addLabels(-1L, [label])

        then:
        thrown(InvalidRequestException)
    }

    def "Stats"() {
        expect:
        service.stats().numberOfBuilds == 12L
    }

    @Unroll("#message")
    def "Search"() {
        given:
        BuildSearch buildSearch = new BuildSearch()
        buildSearch.project = theProject
        buildSearch.branch = theBranch
        if (theLabelKey && theLabelValue) {
            buildSearch.labels.put(theLabelKey, theLabelValue)
        }
        if (theMinBuildNumber) {
            buildSearch.minBuildNumber = theMinBuildNumber
        }
        if (theMaxBuildNumber) {
            buildSearch.maxBuildNumber = theMaxBuildNumber
        }

        when:
        BuildSearchResult result = service.search(buildSearch)

        then:
        result.builds.size() == expected

        and:
        if (theProject) {
            result.builds.project.every {
                it.equals(theProject)
            }
        }

        and:
        if (theBranch) {
            result.builds.branch.every {
                it.equals(theBranch)
            }
        }


        where:
        theProject       | theBranch | theLabelKey        | theLabelValue                | expected | theMinBuildNumber | theMaxBuildNumber | message
        null             | null      | null               | null                         | 10       | null              | null              | "Search(): finds all builds for empty search"
        null             | null      | null               | null                         | 3        | 1                 | 3                 | "Search(): finds all builds with build number = 2"
        'buildz-backend' | null      | null               | null                         | 4        | null              | null              | "Search(): finds all builds of project 'buildz-backend'"
        'buildz-backend' | 'master'  | null               | null                         | 2        | null              | null              | "Search(): finds all builds of project 'buildz-backend' of branch 'master"
        null             | null      | 'technical_branch' | 'feature/some-other-feature' | 2        | null              | null              | "Search(): finds all builds of projects with label technical_branch=feature/some-other-feature"
        null             | null      | 'technical_branch' | 'noSuchBranch'               | 0        | null              | null              | "Search(): is empty for empty lbel sub-search"

    }
}
