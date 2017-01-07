package com.tangled.web.url.path;

import java.io.IOException;

import com.tangled.web.url.common.AbstractParser;
import com.tangled.web.url.protocol.InvalidProtocolException;

public class PathParser extends AbstractParser<String> {
    private int protocolSegmentLength;

    @Override
    public String parse(String url) throws IOException, InvalidProtocolException {
        int pathIndexFrom = url.indexOf(END_OF_DOMAIN_SYMBOL, protocolSegmentLength);

        if (pathIndexFrom == -1 || pathIndexFrom == getLastUrlIndexNumber(url)) {
            return null;
        }

        return getPath(url, pathIndexFrom);
    }

    private String getPath(String url, int pathIndexFrom) {
        int pathIndexTo = getPathIndexTo(url, pathIndexFrom);
        int pathIndexFromWithoutPathSymbol = pathIndexFrom + 1;

        if (pathIndexTo == -1 || pathIndexFrom == pathIndexTo) {
            pathIndexTo = getLastUrlIndexNumber(url);
        }
        return url.substring(pathIndexFromWithoutPathSymbol, pathIndexTo);
    }

    public PathParser withProtocolSegmentLength(int protocolSegmentLength) {
        this.protocolSegmentLength = protocolSegmentLength;
        return this;
    }

    private int getPathIndexTo(String url, int indexFrom) {
        int nextIndexAfterPathBeginningSymbol = indexFrom + 1;
        int indexOfAnchorOrParamsSymbol = getIndexOfAnchorOrParamsSymbol(url, nextIndexAfterPathBeginningSymbol);

        if (indexOfAnchorOrParamsSymbol == -1) {
            return url.lastIndexOf(END_OF_DOMAIN_SYMBOL, indexFrom);
        }

        return indexOfAnchorOrParamsSymbol;
    }

    private int getLastUrlIndexNumber(String url) {
        return url.length() - 1;
    }
}
