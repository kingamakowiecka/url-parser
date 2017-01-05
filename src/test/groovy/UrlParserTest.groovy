import com.tangled.web.exception.InvalidProtocolException
import com.tangled.web.url.UrlParser
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UrlParserTest extends Specification {

    def "Url #url parser smoke test"() {
        given:
        def parser = new UrlParser(url)

        when:
        def urlSegments = parser.parseUrl()

        then:
        urlSegments.protocol == protocol
        urlSegments.domain == "test"

        where:
        url                      || protocol || credentials || domain        || port || path || query || anchor
        "http://www.example.com" || "http"   || null        || "example.com" || null || null || null  || null
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
        "www.example.com"         || null
    }


    def "Throws InvalidProtocolException when protocol is invalid"() {
        given:
        def url = "httpinvalid://www.example.com"
        def parser = new UrlParser(url)

        when:
        parser.parseUrl()

        then:
        thrown InvalidProtocolException
    }
}