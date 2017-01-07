package com.tangled.web.url.domain

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DomainNameParserTest extends Specification {
    def static DOMAIN_NAME_PARSER

    def setupSpec() {
        DOMAIN_NAME_PARSER = new DomainNameParser()
    }

    def "Url #url has properly parsed domain (#domain) with optionals credentials"() {
        when:
        def parsedDomain = DOMAIN_NAME_PARSER
                .withProtocolSegmentLength(protocolLength)
                .withCredentialsSegmentLength(credentialsLength)
                .parse(url)

        then:
        parsedDomain == domain

        where:
        url                               || domain        || credentials || protocolLength || credentialsLength
        "example.com"                     || "example.com" || null        || 0              || 0
        "http://www.example.com"          || "example.com" || null        || 7              || 0
        "www.example.com"                 || "example.com" || null        || 0              || 0
        "http://example.com"              || "example.com" || null        || 7              || 0
        "http://example.com/test?param=p" || "example.com" || null        || 7              || 0
        "http://example.com?param=p"      || "example.com" || null        || 7              || 0
        "http://example.com#text"         || "example.com" || null        || 7              || 0
    }

    def "Throws InvalidDomainException when domain is invalid in #url"() {
        when:
        DOMAIN_NAME_PARSER
                .withProtocolSegmentLength(protocolLength)
                .withCredentialsSegmentLength(0)
                .parse(url)

        then:
        thrown InvalidDomainException

        where:
        url                 || protocolLength
        "http://"           || 7
        "http://fakedomain" || 7
        "?param=1"          || 0
        "http://?param=1"   || 7
    }
}