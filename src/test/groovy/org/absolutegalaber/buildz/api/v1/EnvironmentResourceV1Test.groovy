package org.absolutegalaber.buildz.api.v1

import groovy.json.JsonSlurper
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import net.sf.json.JSONArray
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
        (response.data as JSONObject).artifacts.size() == 2
    }

    def "Get eith invalid name"() {
        when:
        restClient.get([
                path: '/v1/environments/nosuchenv'
        ])

        then:
        thrown(HttpResponseException)
    }

    def "Save"() {
        when:
        HttpResponseDecorator response = restClient.post([
                path: '/v1/environments/',
                body: new JsonSlurper().parseText("""
{
    "name":"my-shiny-new-environment"
}
""")
        ])

        then:
        response.isSuccess()

        and:
        (response.data as JSONObject).id
    }


    def "Verify"() {
        when:
        HttpResponseDecorator response = restClient.post([
                path: '/v1/environments/verify',
                body: new JsonSlurper().parseText("""
    [
    {"project":"buildz-backend", "branch":"master"}
    ]
""")
        ])

        then:
        response.isSuccess()

        and:
        (response.data as JSONArray).size() == 1
    }

    def "Delete"() {
        when:
        HttpResponseDecorator response = restClient.delete([
                path: '/v1/environments/master-test-stage-1'
        ])

        then:
        response.isSuccess()
    }
}
