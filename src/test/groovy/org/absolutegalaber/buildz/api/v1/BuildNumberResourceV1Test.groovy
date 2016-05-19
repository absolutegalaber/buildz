package org.absolutegalaber.buildz.api.v1

import groovyx.net.http.HttpResponseDecorator
import net.sf.json.JSONObject
import org.absolutegalaber.buildz.api.BaseRestSpec

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildNumberResourceV1Test extends BaseRestSpec {

    def "Set(), Current(), Next()"() {
        given:
        String theProject = 'Some-New-Crazy-Project'
        String theBranch = 'master'

        when:
        HttpResponseDecorator setResponse = restClient.post([
                path: '/v1/buildNumbers/set',
                body: [
                        project: theProject,
                        branch : theBranch,
                        count  : 2
                ]
        ])
        HttpResponseDecorator currentResponse = restClient.get([
                path: "/v1/buildNumbers/current/${theProject}/${theBranch}"
        ])
        HttpResponseDecorator nextResponse = restClient.post([
                path: '/v1/buildNumbers/next',
                body: [
                        project: theProject,
                        branch : theBranch
                ]
        ])

        then:
        setResponse.isSuccess()
        currentResponse.isSuccess()
        nextResponse.isSuccess()

        and:
        (currentResponse.data as JSONObject).count == 2
        (nextResponse.data as JSONObject).count == 3
    }
}
