package org.absolutegalaber.buildz.api.v1

import org.absolutegalaber.buildz.api.BaseRestSpec
import org.hamcrest.Matchers
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildNumberResourceV1Test extends BaseRestSpec {

    def "Current() for existing project"() {
        given:
        MockHttpServletRequestBuilder currentRequest = get('/v1/buildNumbers/current/buildz-backend/master')

        when:
        ResultActions result = mvc.perform(currentRequest)

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(jsonPath('count', Matchers.is(1)))
    }

    def "Current() on new project"() {
        given:
        MockHttpServletRequestBuilder currentRequest = get('/v1/buildNumbers/current/new-project/master')

        when:
        ResultActions result = mvc.perform(currentRequest)

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(jsonPath('count', Matchers.is(0)))
    }

    def "Next() and Set()"() {
        given: 'A Request to Next() and a Request to Set() Method'
        MockHttpServletRequestBuilder request = post('/v1/buildNumbers/next')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"project":"some-crazy-project", "branch":"master"}')
        MockHttpServletRequestBuilder setRequest = post('/v1/buildNumbers/set')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"project":"some-crazy-project", "branch":"master", "count":"10"}')

        when:
        //Call next
        ResultActions first = mvc.perform(request)
        //Call next again
        ResultActions second = mvc.perform(request)
        //set the current build number to something different
        ResultActions third = mvc.perform(setRequest)
        //Call next once again
        ResultActions fourth = mvc.perform(request)

        then:
        first.andExpect(status().isOk())
        second.andExpect(status().isOk())
        third.andExpect(status().isOk())
        fourth.andExpect(status().isOk())

        and:
        first.andExpect(jsonPath('count', Matchers.is(1)))
        second.andExpect(jsonPath('count', Matchers.is(2)))
        fourth.andExpect(jsonPath('count', Matchers.is(11)))
    }
}
