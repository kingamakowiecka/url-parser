package com.tangled.web.url.domain;

import static com.tangled.web.url.common.SymbolIndexHelper.getIndex;
import static com.tangled.web.url.domain.UserCredentialsParser.getCredentialsSegmentLength;
import static com.tangled.web.url.protocol.ProtocolParser.getProtocolSegmentLength;

import java.io.IOException;

import com.tangled.web.url.common.Statics;
import com.tangled.web.url.protocol.InvalidProtocolException;

public class DomainNameParser {

    public static String parseDomainName(String url) throws IOException, InvalidProtocolException {
        int indexFrom = getDomainNameIndexFrom(url);
        int indexTo = getDomainNameIndexTo(url, indexFrom);

        return indexTo > 0 ? url.substring(indexFrom, indexTo) : url.substring(indexFrom);
    }

    private static int getDomainNameIndexFrom(String url) throws IOException, InvalidProtocolException {
        int indexFrom = getProtocolSegmentLength(url);
        indexFrom += getCredentialsSegmentLength(url, indexFrom);
        indexFrom += getWwwSegmentLength(url, indexFrom);
        return indexFrom;
    }

    private static int getDomainNameIndexTo(String url, int indexFrom) {
        int endOfDomainIndex = getIndex(url, Statics.END_OF_DOMAIN_SYMBOL, indexFrom);
        int queryParamIndex = getIndex(url, Statics.QUERY_PARAM_SYMBOL, indexFrom);
        int anchorIndex = getIndex(url, Statics.ANCHOR_SYMBOL, indexFrom);

        if (endOfDomainIndex > 0) {
            return endOfDomainIndex;
        } else if (queryParamIndex > 0) {
            return queryParamIndex;
        } else if (anchorIndex > 0) {
            return anchorIndex;
        }

        return 0;
    }

    private static int getWwwSegmentLength(String url, int index) {
        int wwwSegmentLength = 0;
        String wwwSegment = Statics.WWW + Statics.URL_SEPARATOR;
        if (url.substring(index, index + wwwSegment.length()).equals(wwwSegment)) {
            wwwSegmentLength = wwwSegment.length();
        }
        return wwwSegmentLength;
    }
}
