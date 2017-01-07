package com.tangled.web.url

import com.tangled.web.url.anchor.AnchorParser
import com.tangled.web.url.domain.DomainNameParser
import com.tangled.web.url.domain.UserCredentialsParser
import com.tangled.web.url.param.ParamParser
import com.tangled.web.url.path.PathParser
import com.tangled.web.url.protocol.ProtocolParser
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UrlParserTest extends Specification {
    def static USER_CREDENTIALS
    def static PARAMS_MAP
    def static URL_PARSER

    def setupSpec() {
        USER_CREDENTIALS = UserCredentials.builder().user("user").password("pass").build()

        PARAMS_MAP = new HashMap()
        PARAMS_MAP.put("first_param", "p")
        PARAMS_MAP.put("second_param", "p2")
        URL_PARSER = new UrlParser(new ProtocolParser(), new UserCredentialsParser(), new DomainNameParser(),
                new PathParser(), new ParamParser(), new AnchorParser())
    }

    def "Url parser smoke test"() {
        given:
        def url = "http://user:pass@example.com/path?first_param=p&second_param=p2#anchor_text"
        def protocol = "http"
        def domain = "example.com"
        def path = "path"
        def anchor = "anchor_text"

        when:
        def urlSegments = URL_PARSER.parseUrl(url)

        then:
        urlSegments.protocol == protocol
        urlSegments.domain == domain
        urlSegments.credentials == USER_CREDENTIALS
        urlSegments.params == PARAMS_MAP
        urlSegments.path == path
        urlSegments.anchor == anchor
    }
}