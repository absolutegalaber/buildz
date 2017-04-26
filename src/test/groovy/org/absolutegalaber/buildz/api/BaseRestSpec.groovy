package org.absolutegalaber.buildz.api

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.absolutegalaber.buildz.BaseBuildzSpec

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BaseRestSpec extends BaseBuildzSpec {
    RESTClient restClient = new RESTClient("http://localhost:8080", ContentType.JSON);
}
