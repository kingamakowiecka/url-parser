import com.tangled.web.url.UrlParser
import com.tangled.web.url.UserCredentials
import com.tangled.web.url.param.InvalidParamException
import com.tangled.web.url.protocol.InvalidProtocolException
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UrlParserTest extends Specification {
    def static UserCredentials USER_CREDENTIALS;
    def static Map PARAMS_MAP

    def setup() {
        USER_CREDENTIALS = UserCredentials.builder().user("user").password("pass").build()

        PARAMS_MAP = new HashMap()
        PARAMS_MAP.put("first_param", "p")
        PARAMS_MAP.put("second_param", "p2")
    }

    def "Url #url parser smoke test"() {
        given:
        def url = "http://user:pass@example.com?first_param=p&second_param=p2"
        def protocol = "http"
        def domain = "example.com"

        def parser = new UrlParser(url)

        when:
        def urlSegments = parser.parseUrl()

        then:
        urlSegments.protocol == protocol
        urlSegments.domain == domain
        urlSegments.credentials == USER_CREDENTIALS
        urlSegments.params == PARAMS_MAP
    }

    def "Url #url has properly parsed protocol (#protocol)"() {
        given:
        def parser = new UrlParser(url)

        when:
        def urlSegments = parser.parseUrl()

        then:
        urlSegments.protocol == protocol

        where:
        url                       || protocol
        "http://www.example.com"  || "http"
        "ftp://www.example.com"   || "ftp"
        "https://www.example.com" || "https"
        "HTTP://www.example.com"  || "http"
        "www.example.com"         || null
    }


    def "Throws InvalidProtocolException when protocol is invalid in #url"() {
        given:
        def parser = new UrlParser(url)

        when:
        parser.parseUrl()

        then:
        thrown InvalidProtocolException

        where:
        url                             || _
        "httpinvalid://www.example.com" || _
        "http:www.example.com"          || _
        "http:/www.example.com"         || _
    }

    def "Url #url has properly parsed domain (#domain) with optionals credentials"() {
        given:
        def parser = new UrlParser(url)

        when:
        def urlSegments = parser.parseUrl()

        then:
        urlSegments.domain == domain
        urlSegments.credentials == credentials

        where:
        url                                 || domain        || credentials
        "example.com"                       || "example.com" || null
        "http://www.example.com"            || "example.com" || null
        "www.example.com"                   || "example.com" || null
        "http://example.com"                || "example.com" || null
        "http://example.com/test?param=p"   || "example.com" || null
        "http://example.com?param=p"        || "example.com" || null
        "http://example.com#text"           || "example.com" || null
        "http://user:pass@example.com#text" || "example.com" || USER_CREDENTIALS
        "http://user:pass@example.com"      || "example.com" || USER_CREDENTIALS
        "user:pass@example.com"             || "example.com" || USER_CREDENTIALS
    }

    def "Url #url has properly parsed query params (#params)"() {
        given:
        def parser = new UrlParser(url)

        when:
        def urlSegments = parser.parseUrl()

        then:
        urlSegments.params == params

        where:
        url                                                     || params
        "http://example.com?first_param=p&second_param=p2"      || PARAMS_MAP
        "example.com?first_param=p&second_param=p2"             || PARAMS_MAP
        "http://example.com?first_param=p&second_param=p2#text" || PARAMS_MAP
        "http://example.com/test?first_param=p&second_param=p2" || PARAMS_MAP
    }

    def "Throws InvalidParamException when params list is invalid in #url"() {
        given:
        def url = "http://example.com/test?first_param&second_param=p";
        def parser = new UrlParser(url)

        when:
        parser.parseUrl()

        then:
        thrown InvalidParamException
    }
}