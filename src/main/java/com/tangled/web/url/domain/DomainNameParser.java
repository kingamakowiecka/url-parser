package com.tangled.web.url.domain;

import static com.tangled.web.url.common.Statics.ANCHOR_SYMBOL;
import static com.tangled.web.url.common.Statics.DOMAIN_NAME_SEPARATOR;
import static com.tangled.web.url.common.Statics.END_OF_DOMAIN_SYMBOL;
import static com.tangled.web.url.common.Statics.QUERY_PARAM_SYMBOL;
import static com.tangled.web.url.common.Statics.URL_SEPARATOR;
import static com.tangled.web.url.common.Statics.WWW;
import static com.tangled.web.url.common.SymbolIndexHelper.getIndex;
import static com.tangled.web.url.domain.UserCredentialsParser.getCredentialsSegmentLength;
import static com.tangled.web.url.protocol.ProtocolParser.getProtocolSegmentLength;

import java.io.IOException;

import com.tangled.web.url.protocol.InvalidProtocolException;

public class DomainNameParser {

    public static String parseDomainName(String url) throws IOException, InvalidProtocolException, InvalidDomainException {
        int indexFrom = getDomainNameIndexFrom(url);
        int indexTo = getDomainNameIndexTo(url, indexFrom);

        String domain = indexTo > 0 ? url.substring(indexFrom, indexTo) : url.substring(indexFrom);
        verifyDomainNameStructure(domain);

        return domain;
    }

    private static void verifyDomainNameStructure(String domain) throws InvalidDomainException {
        if (!domain.contains(DOMAIN_NAME_SEPARATOR)) {
            throw new InvalidDomainException();
        }
    }

    private static int getDomainNameIndexFrom(String url) throws IOException, InvalidProtocolException, InvalidDomainException {
        int indexFrom = getProtocolSegmentLength(url);
        indexFrom += getCredentialsSegmentLength(url, indexFrom);
        indexFrom += getWwwSegmentLength(url, indexFrom);
        return indexFrom;
    }

    private static int getDomainNameIndexTo(String url, int indexFrom) {
        int endOfDomainIndex = getIndex(url, END_OF_DOMAIN_SYMBOL, indexFrom);
        int queryParamIndex = getIndex(url, QUERY_PARAM_SYMBOL, indexFrom);
        int anchorIndex = getIndex(url, ANCHOR_SYMBOL, indexFrom);

        if (endOfDomainIndex > 0) {
            return endOfDomainIndex;
        } else if (queryParamIndex > 0) {
            return queryParamIndex;
        } else if (anchorIndex > 0) {
            return anchorIndex;
        }

        return 0;
    }

    private static int getWwwSegmentLength(String url, int index) throws InvalidDomainException {
        int wwwSegmentLength = 0;
        verifyUrlLength(url, index);

        String wwwSegment = WWW + URL_SEPARATOR;
        if (url.substring(index, index + wwwSegment.length()).equals(wwwSegment)) {
            wwwSegmentLength = wwwSegment.length();
        }
        return wwwSegmentLength;
    }

    private static void verifyUrlLength(String url, int index) throws InvalidDomainException {
        if (url.length() == index) {
            throw new InvalidDomainException();
        }
    }
}
