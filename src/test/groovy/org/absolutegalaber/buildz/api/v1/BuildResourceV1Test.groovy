package org.absolutegalaber.buildz.api.v1

import groovy.json.JsonSlurper
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import net.sf.json.JSONObject
import org.absolutegalaber.buildz.api.BaseRestSpec
import spock.lang.Ignore

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Ignore
class BuildResourceV1Test extends BaseRestSpec {
    def "Stats"() {
        when:
        HttpResponseDecorator response = restClient.get([
                path: '/v1/builds/stats'
        ])
        def data = response.data as JSONObject

        then:
        data.projects

        and:
        data.numberOfBuilds
    }

    def "Search"() {
        when:
        //search for project 'buildz-backend'
        HttpResponseDecorator response = restClient.post([
                path: '/v1/builds/search',
                body: [
                        project: 'buildz-backend'
                ]
        ])
        def data = response.data as JSONObject

        then:
        data.builds.size()

    }

    def "Get"() {
        when:
        HttpResponseDecorator response = restClient.get([
                path: '/v1/builds/1'
        ])
        def data = response.data as JSONObject

        then:
        data.id == 1
        data.project == 'buildz-backend'
    }

    def "OfEnvironment"() {
        when:
        HttpResponseDecorator response = restClient.get([
                path: '/v1/builds/ofEnvironment/feature-test-stage-1'
        ])
        def data = response.data as JSONObject

        then:
        data.builds.size() == 2
    }

    def "Get Missing Build"() {
        when:
        restClient.get([
                path: '/v1/builds/-1'
        ])

        then:
        thrown(HttpResponseException)
    }

    def "Create and AddLabel"() {
        when:
        HttpResponseDecorator response = restClient.post([
                path: '/v1/builds/create',
                body: [
                        project    : 'buildz-backend',
                        branch     : 'cool-new-feature',
                        buildNumber: 1
                ]
        ])
        def data = response.data as JSONObject

        HttpResponseDecorator addLabelResponse = restClient.post([
                path: "/v1/builds/addLabels/${data.id}",
                body: new JsonSlurper().parseText("""
[{"key":"crazyKey", "value":"crazyValue"}]
""")
        ])
        def labeledData = addLabelResponse.data as JSONObject

        then:
        data.id

        and:
        labeledData.id == data.id

        and:
        data.project == 'buildz-backend'
        data.branch == 'cool-new-feature'
        data.buildNumber == 1

        and:
        labeledData.labels.size() == 1
    }
}
