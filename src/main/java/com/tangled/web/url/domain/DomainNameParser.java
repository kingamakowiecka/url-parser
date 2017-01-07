package com.tangled.web.url.domain;

import java.io.IOException;

import com.tangled.web.url.common.AbstractParser;
import com.tangled.web.url.protocol.InvalidProtocolException;

public class DomainNameParser extends AbstractParser {
    private int protocolSegmentLength;
    private int credentialSegmentLength;

    public String parse(String url) throws IOException, InvalidDomainException, InvalidProtocolException {
        int indexFrom = getDomainNameIndexFrom(url);
        int indexTo = getDomainNameIndexTo(url, indexFrom);

        String domain = getDomainName(url, indexFrom, indexTo);
        verifyDomainNameStructure(domain);

        return domain;
    }

    public DomainNameParser withProtocolSegmentLength(int protocolSegmentLength) {
        this.protocolSegmentLength = protocolSegmentLength;
        return this;
    }

    public DomainNameParser withCredentialsSegmentLength(int credentialSegmentLength) {
        this.credentialSegmentLength = credentialSegmentLength;
        return this;
    }

    private int getDomainNameIndexFrom(String url) throws IOException,
            InvalidProtocolException,
            InvalidDomainException {
        int indexFrom = protocolSegmentLength;
        if (credentialSegmentLength != -1) {
            indexFrom += credentialSegmentLength;
        }
        indexFrom += getWwwSegmentLength(url, indexFrom);
        return indexFrom;
    }

    private int getDomainNameIndexTo(String url, int indexFrom) {
        int endOfDomainIndex = url.indexOf(END_OF_DOMAIN_SYMBOL, indexFrom);
        if (endOfDomainIndex != -1) {
            return endOfDomainIndex;
        } else {
            int nextIndexAfterDomainBeginningSymbol = indexFrom + 1;
            return getIndexOfAnchorOrParamsSymbol(url, nextIndexAfterDomainBeginningSymbol);
        }
    }

    private int getWwwSegmentLength(String url, int index) throws InvalidDomainException {
        int wwwSegmentLength = 0;
        verifyUrlLength(url, index);

        String wwwSegment = WWW + URL_SEPARATOR;
        if (url.substring(index, index + wwwSegment.length()).equals(wwwSegment)) {
            wwwSegmentLength = wwwSegment.length();
        }
        return wwwSegmentLength;
    }

    private void verifyUrlLength(String url, int index) throws InvalidDomainException {
        if (url.length() == index) {
            throw new InvalidDomainException();
        }
    }

    private String getDomainName(String url, int indexFrom, int indexTo) {
        return indexTo != -1 ? url.substring(indexFrom, indexTo) : url.substring(indexFrom);
    }

    private void verifyDomainNameStructure(String domain) throws InvalidDomainException {
        if (!domain.contains(DOMAIN_NAME_SEPARATOR)) {
            throw new InvalidDomainException();
        }
    }
}
