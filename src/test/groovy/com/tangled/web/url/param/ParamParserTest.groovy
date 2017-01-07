package com.tangled.web.url.param

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ParamParserTest extends Specification {
    def static PARAMS_MAP
    def static PARAM_PARSER

    def setupSpec() {
        PARAM_PARSER = new ParamParser()

        PARAMS_MAP = new HashMap()
        PARAMS_MAP.put("first_param", "p")
        PARAMS_MAP.put("second_param", "p2")
    }

    def "Url #url has properly parsed query params (#params)"() {
        when:
        def parsedParams = PARAM_PARSER.parse(url)

        then:
        parsedParams == params

        where:
        url                                                     || params
        "http://example.com?first_param=p&second_param=p2"      || PARAMS_MAP
        "example.com?first_param=p&second_param=p2"             || PARAMS_MAP
        "http://example.com?first_param=p&second_param=p2#text" || PARAMS_MAP
        "http://example.com/test?first_param=p&second_param=p2" || PARAMS_MAP
        "http://example.com/test"                               || null
    }

    def "Throws InvalidParamException when params list is invalid in #url"() {
        given:
        def url = "http://example.com/test?first_param&second_param=p"

        when:
        PARAM_PARSER.parse(url)

        then:
        thrown InvalidParamException
    }
}