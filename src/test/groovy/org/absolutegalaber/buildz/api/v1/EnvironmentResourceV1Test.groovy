package org.absolutegalaber.buildz.api.v1

import org.absolutegalaber.buildz.api.BaseRestSpec
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by Josip.Mihelko @ Gmail
 */
class EnvironmentResourceV1Test extends BaseRestSpec {

    def "Get()"() {
        expect:
        mvc.perform(get('/v1/environments/feature-test-stage-1'))
                .andExpect(status().isOk())
    }

    def "Get() Not Found"() {
        expect:
        mvc.perform(get('/v1/environments/no-such-env'))
                .andExpect(status().isBadRequest())
    }

    def "Save() and Delete()"() {
        expect:
        mvc.perform(
                post('/v1/environments/')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{"name":"new-env"}'))
                .andExpect(status().isOk())
        mvc.perform(delete('/v1/environments/new-env'))
                .andExpect(status().isOk())
    }

    def "Verify()"() {
        expect:
        mvc.perform(
                post('/v1/environments/verify')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('[{"project":"buildz-backend"}]')

        )
                .andExpect(status().isOk())
    }

}
