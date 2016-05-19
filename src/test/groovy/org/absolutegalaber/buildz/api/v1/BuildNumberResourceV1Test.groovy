package org.absolutegalaber.buildz.api.v1

import groovyx.net.http.HttpResponseDecorator
import net.sf.json.JSONObject
import org.absolutegalaber.buildz.api.BaseRestSpec

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildNumberResourceV1Test extends BaseRestSpec {


    def "Next"() {
        when:
        HttpResponseDecorator response = restClient.post([
                path: '/v1/buildNumbers/next',
                body: [
                        project: 'buildz-testproject',
                        branch : 'master'
                ]
        ])
        def responseData = response.data as JSONObject

        then:
        response.isSuccess()

        and:
        responseData.count == 1


    }

    def "Current"() {

    }

    def "Set"() {

    }
}
