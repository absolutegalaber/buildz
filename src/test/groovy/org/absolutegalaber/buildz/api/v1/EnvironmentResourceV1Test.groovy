package org.absolutegalaber.buildz.api.v1

import groovy.json.JsonSlurper
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import net.sf.json.JSONObject
import org.absolutegalaber.buildz.api.BaseRestSpec

/**
 * Created by Josip.Mihelko @ Gmail
 */
class EnvironmentResourceV1Test extends BaseRestSpec {

    def "Get"() {
        when:
        HttpResponseDecorator response = restClient.get([
                path: '/v1/environments/feature-test-stage-1'
        ])

        then:
        response.isSuccess()

        and:
        (response.data as JSONObject).id
        (response.data as JSONObject).artifacts.size() == 1
    }

    def "Get eith invalid name"() {
        when:
        restClient.get([
                path: '/v1/environments/nosuchenv'
        ])

        then:
        thrown(HttpResponseException)
    }

    def "Create"() {
        when:
        HttpResponseDecorator response = restClient.post([
                path: '/v1/environments/create',
                body: [name: 'shiny-nw-environment']
        ])

        then:
        response.isSuccess()

        and:
        (response.data as JSONObject).id
    }

    def "AddArtifact"() {
        when:
        HttpResponseDecorator response = restClient.post([
                path: '/v1/environments/addArtifact/master-test-stage-1',
                body: new JsonSlurper().parseText("""
{
    "project": "buildz-backend",
    "labels": {
        "some-crazy-docker-image-name": "docker-image",
        "technical_branch": "feature"
    }
}
""")
        ])

        then:
        response.isSuccess()
    }
}
