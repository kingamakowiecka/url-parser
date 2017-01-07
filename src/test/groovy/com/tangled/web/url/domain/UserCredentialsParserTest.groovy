package com.tangled.web.url.domain

import com.tangled.web.url.UserCredentials
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UserCredentialsParserTest extends Specification {
    def static USER_CREDENTIALS
    def static USER_CREDENTIALS_PARSER

    def setupSpec() {
        USER_CREDENTIALS = UserCredentials.builder().user("user").password("pass").build()
        USER_CREDENTIALS_PARSER = new UserCredentialsParser()
    }

    def "Url #url has properly parsed credentials (#credentials)"() {
        when:
        def parsedCredentials = USER_CREDENTIALS_PARSER
                .withProtocolSegmentLength(protocolLength)
                .parse(url)

        then:
        parsedCredentials == credentials

        where:
        url                                 || credentials      || protocolLength
        "http://user:pass@example.com#text" || USER_CREDENTIALS || 7
        "http://user:pass@example.com"      || USER_CREDENTIALS || 7
        "user:pass@example.com"             || USER_CREDENTIALS || 0
        "example.com"                       || null             || 0
    }
}