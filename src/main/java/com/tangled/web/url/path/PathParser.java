package com.tangled.web.url.path;

import static com.tangled.web.url.common.Statics.ANCHOR_SYMBOL;
import static com.tangled.web.url.common.Statics.END_OF_DOMAIN_SYMBOL;
import static com.tangled.web.url.common.Statics.QUERY_PARAM_SYMBOL;
import static com.tangled.web.url.common.SymbolIndexHelper.getIndex;

import java.io.IOException;

import com.tangled.web.url.protocol.InvalidProtocolException;
import com.tangled.web.url.protocol.ProtocolParser;

public class PathParser {
    public static String parse(String url) throws IOException, InvalidProtocolException {
        int pathIndexFrom = getIndex(url, END_OF_DOMAIN_SYMBOL,
                ProtocolParser.getProtocolSegmentLength(url));
        int pathIndexTo = getPathIndexTo(url, pathIndexFrom);

        if (pathIndexFrom == -1) {
            return null;
        }

        int pathIndexFromWithoutPathSymbol = pathIndexFrom + 1;
        return pathIndexTo == 0 || pathIndexFrom == pathIndexTo ? url.substring(
                pathIndexFromWithoutPathSymbol) : url.substring
                (pathIndexFromWithoutPathSymbol, pathIndexTo);
    }

    private static int getPathIndexTo(String url, int indexFrom) {
        int queryParamIndex = getIndex(url, QUERY_PARAM_SYMBOL, indexFrom + 1);
        int anchorIndex = getIndex(url, ANCHOR_SYMBOL, indexFrom + 1);
        int endOfPathIndex = getIndex(url, END_OF_DOMAIN_SYMBOL, indexFrom + 1);

        if (queryParamIndex != -1) {
            return queryParamIndex;
        } else if (anchorIndex != -1) {
            return anchorIndex;
        } else if (endOfPathIndex != -1) {
            return endOfPathIndex;
        }

        return 0;
    }
}
