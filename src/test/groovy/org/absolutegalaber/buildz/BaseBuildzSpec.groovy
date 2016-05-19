package org.absolutegalaber.buildz

import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringApplicationConfiguration(classes = BaseBuildzTestConfig.class)
@WebAppConfiguration
@Transactional
class BaseBuildzSpec extends Specification {
}
