package org.absolutegalaber.buildz.api.v1

import org.absolutegalaber.buildz.api.BaseRestSpec
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildResourceV1Test extends BaseRestSpec {
    def "Stats"() {
        expect:
        mvc.perform(get('/v1/builds/stats'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('projects').isArray())
                .andExpect(jsonPath('environments').isArray())
                .andExpect(jsonPath('numberOfBuilds').isNumber())
                .andExpect(jsonPath('numberOfLabels').isNumber())
    }

    def "Search()"() {
        expect:
        mvc.perform(
                post('/v1/builds/search')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{"project":"buildz-backend"}')
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath('builds').isArray())
    }

    def "Get()"() {
        expect:
        mvc.perform(get('/v1/builds/1'))
                .andExpect(status().isOk())
    }

    def "Get() NotFound"() {
        expect:
        mvc.perform(get('/v1/builds/-1'))
                .andExpect(status().isBadRequest())
    }

    def "Create() and AddLabel()"() {
        expect:
        mvc.perform(
                post('/v1/builds/create')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{"project":"some-cool-project", "branch":"master", "buildNumber":"1"}')
        )
                .andExpect(status().isOk())
    }

    def "AddLabels()"() {
        expect:
        mvc.perform(
                post('/v1/builds/addLabels/15')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('[{"key":"labelKey", "value":"labelValue"}]')
        )
                .andExpect(status().isOk())
    }

    def "OfEnvironment()"() {
        expect:
        mvc.perform(get('/v1/builds/ofEnvironment/feature-test-stage-1'))
                .andExpect(status().isOk())

    }
}
