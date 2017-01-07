package com.tangled.web.url.path;

import java.io.IOException;

import com.tangled.web.url.common.AbstractParser;
import com.tangled.web.url.protocol.InvalidProtocolException;

public class PathParser extends AbstractParser {
    private int protocolSegmentLength;

    public String parse(String url) throws IOException, InvalidProtocolException {
        int pathIndexFrom = getIndex(url, END_OF_DOMAIN_SYMBOL, protocolSegmentLength);
        int pathIndexTo = getPathIndexTo(url, pathIndexFrom);

        if (pathIndexFrom == -1) {
            return null;
        }

        int pathIndexFromWithoutPathSymbol = pathIndexFrom + 1;

        return pathIndexTo != -1 || pathIndexFrom == pathIndexTo ?
                url.substring(pathIndexFromWithoutPathSymbol, pathIndexTo)
                : url.substring(pathIndexFromWithoutPathSymbol);
    }

    public PathParser withProtocolSegmentLength(int protocolSegmentLength) {
        this.protocolSegmentLength = protocolSegmentLength;
        return this;
    }

    private int getPathIndexTo(String url, int indexFrom) {
        int nextIndexAfterPathBeginningSymbol = indexFrom + 1;
        return getIndexOfDomainEndingSymbol(url, nextIndexAfterPathBeginningSymbol);
    }
}
