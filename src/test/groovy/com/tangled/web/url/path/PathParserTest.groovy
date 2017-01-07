package com.tangled.web.url.path

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PathParserTest extends Specification {
    def static PATH_PARSER

    def setupSpec() {
        PATH_PARSER = new PathParser()
    }

    def "Url #url has properly parsed path (#path)"() {
        when:
        def parsedPath = PATH_PARSER.withProtocolSegmentLength(protocolLength).parse(url)

        then:
        parsedPath == path

        where:
        url                                                     || path         || protocolLength
        "http://example.com/path?first_param=p&second_param=p2" || "path"       || 7
        "http://example.com/path/"                              || "path"       || 7
        "http://example.com/path/path2?param=p"                 || "path/path2" || 7
        "http://example.com?param=p"                            || null         || 7
        "http://example.com?param=p/"                           || null         || 7
    }
}