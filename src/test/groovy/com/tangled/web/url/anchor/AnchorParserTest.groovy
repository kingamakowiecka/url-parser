package com.tangled.web.url.anchor

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AnchorParserTest extends Specification {
    def static ANCHOR_PARSER

    def setupSpec() {
        ANCHOR_PARSER = new AnchorParser()
    }

    def "Url #url has properly parsed anchor (#anchor)"() {
        when:
        def parsedAnchor = ANCHOR_PARSER.parse(url)

        then:
        parsedAnchor == anchor

        where:
        url                             || anchor
        "http://example.com#text"       || "text"
        "http://example.com#text/text2" || "text/text2"
        "http://example.com#text:text2" || "text:text2"
        "http://example.com#text@text2" || "text@text2"
    }
}