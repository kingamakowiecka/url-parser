package com.tangled.web;

import java.io.IOException;

import com.tangled.web.url.UrlParser;
import com.tangled.web.url.UrlSegments;
import com.tangled.web.url.anchor.AnchorParser;
import com.tangled.web.url.common.InvalidUrlException;
import com.tangled.web.url.domain.DomainNameParser;
import com.tangled.web.url.domain.UserCredentialsParser;
import com.tangled.web.url.param.ParamParser;
import com.tangled.web.url.path.PathParser;
import com.tangled.web.url.protocol.ProtocolParser;

public class App {
    private static final String URL = "http://example.com/test_path?param=123&next_param=456#just_simpleText";

    public static void main(String[] args) throws IOException, InvalidUrlException {
        ProtocolParser protocolParser = new ProtocolParser();
        UserCredentialsParser userCredentialsParser = new UserCredentialsParser();
        DomainNameParser domainNameParser = new DomainNameParser();
        PathParser pathParser = new PathParser();
        ParamParser paramParser = new ParamParser();
        AnchorParser anchorParser = new AnchorParser();

        UrlParser urlParser = new UrlParser(protocolParser, userCredentialsParser, domainNameParser, pathParser,
                paramParser, anchorParser);

        UrlSegments urlSegments = urlParser.parseUrl(URL);

        System.out.println("Just Simple Example of URL parser.");
        System.out.println("For more details please just look at Spock Unit Tests.");
        System.out.println("URL: " + URL);
        System.out.println("Protocol: " + urlSegments.getProtocol());
        System.out.println("UserCredentials: " + urlSegments.getCredentials());
        System.out.println("Domain: " + urlSegments.getDomain());
        System.out.println("Path: " + urlSegments.getPath());
        System.out.println("Params: " + urlSegments.getParams());
        System.out.println("Anchor: " + urlSegments.getAnchor());
    }
}
