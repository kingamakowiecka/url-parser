package com.tangled.web.url;

import java.io.IOException;

import com.tangled.web.url.anchor.AnchorParser;
import com.tangled.web.url.common.InvalidUrlException;
import com.tangled.web.url.domain.DomainNameParser;
import com.tangled.web.url.domain.UserCredentialsParser;
import com.tangled.web.url.param.ParamParser;
import com.tangled.web.url.path.PathParser;
import com.tangled.web.url.protocol.ProtocolParser;

public class UrlParser {
    private ProtocolParser protocolParser;
    private UserCredentialsParser userCredentialsParser;
    private DomainNameParser domainNameParser;
    private PathParser pathParser;
    private ParamParser paramParser;
    private AnchorParser anchorParser;

    public UrlParser(ProtocolParser protocolParser, UserCredentialsParser userCredentialsParser, DomainNameParser domainNameParser, PathParser pathParser, ParamParser paramParser, AnchorParser anchorParser) {
        this.protocolParser = protocolParser;
        this.userCredentialsParser = userCredentialsParser;
        this.domainNameParser = domainNameParser;
        this.pathParser = pathParser;
        this.paramParser = paramParser;
        this.anchorParser = anchorParser;
    }

    public UrlSegments parseUrl(String url) throws IOException, InvalidUrlException {
        String protocol = protocolParser.parse(url);
        int protocolSegmentLength = protocolParser.getProtocolSegmentLength(protocol);
        int credentialsSegmentLength = userCredentialsParser.getCredentialsSegmentLength(url, protocolSegmentLength);
        return UrlSegments.builder()
                .protocol(protocol)
                .credentials(userCredentialsParser
                        .withProtocolSegmentLength(protocolSegmentLength)
                        .parse(url))
                .domain(domainNameParser
                        .withProtocolSegmentLength(protocolSegmentLength)
                        .withCredentialsSegmentLength(credentialsSegmentLength)
                        .parse(url))
                .path(pathParser
                        .withProtocolSegmentLength(protocolSegmentLength)
                        .parse(url))
                .params(paramParser.parse(url))
                .anchor(anchorParser.parse(url))
                .build();
    }
}
