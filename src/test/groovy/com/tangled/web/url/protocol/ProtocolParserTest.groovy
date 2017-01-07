package com.tangled.web.url.protocol

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ProtocolParserTest extends Specification {
    def static PROTOCOL_PARSER

    def setupSpec() {
        PROTOCOL_PARSER = new ProtocolParser()
    }

    def "Url #url has properly parsed protocol (#protocol)"() {
        when:
        def parsedProtocol = PROTOCOL_PARSER.parse(url)

        then:
        parsedProtocol == protocol

        where:
        url                       || protocol
        "http://www.example.com"  || "http"
        "ftp://www.example.com"   || "ftp"
        "https://www.example.com" || "https"
        "HTTP://www.example.com"  || "http"
        "www.example.com"         || null
    }


    def "Throws InvalidProtocolException when protocol is invalid in #url"() {
        when:
        PROTOCOL_PARSER.parse(url)

        then:
        thrown InvalidProtocolException

        where:
        url                             || _
        "httpinvalid://www.example.com" || _
        "http:www.example.com"          || _
        "http:/www.example.com"         || _
    }
}