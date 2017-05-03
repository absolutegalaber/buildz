package org.absolutegalaber.buildz.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.absolutegalaber.buildz.BaseBuildzTestConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest(classes = BaseBuildzTestConfig.class)
@ContextConfiguration
class BaseRestSpec extends Specification {
    @Autowired
    protected WebApplicationContext context
    protected MockMvc mvc
    protected ObjectMapper jsonFactory = new ObjectMapper()

    def setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }

}
