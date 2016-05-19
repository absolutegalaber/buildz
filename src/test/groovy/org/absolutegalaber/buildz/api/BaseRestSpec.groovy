package org.absolutegalaber.buildz.api

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.absolutegalaber.buildz.BaseBuildzSpec
import org.springframework.boot.test.IntegrationTest

/**
 * Created by Josip.Mihelko @ Gmail
 */
@IntegrationTest
class BaseRestSpec extends BaseBuildzSpec {
    RESTClient restClient = new RESTClient("http://localhost:8080", ContentType.JSON);
}
