import com.tangled.web.url.UrlParser
import com.tangled.web.url.UserCredentials
import com.tangled.web.url.protocol.InvalidProtocolException
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UrlParserTest extends Specification {
    private static UserCredentials USER_CREDENTIALS = UserCredentials.builder().user("user").password("pass").build()

    def "Url #url parser smoke test"() {
        given:
        def url = "http://user:pass@example.com"
        def protocol = "http"
        def domain = "example.com"

        def parser = new UrlParser(url)

        when:
        def urlSegments = parser.parseUrl()

        then:
        urlSegments.protocol == protocol
        urlSegments.domain == domain
        urlSegments.credentials == USER_CREDENTIALS
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
}