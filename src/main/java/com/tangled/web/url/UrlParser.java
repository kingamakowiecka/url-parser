package com.tangled.web.url;

import java.io.IOException;

import com.tangled.web.url.domain.DomainNameParser;
import com.tangled.web.url.domain.UserCredentialsParser;
import com.tangled.web.url.protocol.InvalidProtocolException;
import com.tangled.web.url.protocol.ProtocolParser;

public class UrlParser {
    private String url;

    public UrlParser(String url) {
        this.url = url;
    }

    public UrlSegments parseUrl() throws IOException, InvalidProtocolException {
        return UrlSegments.builder()
                .protocol(ProtocolParser.parse(url))
                .credentials(UserCredentialsParser.parseUserCredentials(url))
                .domain(DomainNameParser.parseDomainName(url))
                .build();
    }
}
