package com.tangled.web.url.common;

import java.io.IOException;

import com.google.common.base.Strings;

public abstract class AbstractParser {
    public static final String PROTOCOL_SLASHES = "//";
    public static final String PROTOCOL_COLON = ":";
    public static final String WWW = "www";
    public static final String URL_SEPARATOR = ".";
    public static final String END_OF_DOMAIN_SYMBOL = "/";
    public static final String QUERY_PARAM_SYMBOL = "?";
    public static final String ANCHOR_SYMBOL = "#";
    public static final String CREDENTIALS_SYMBOL = "@";
    public static final String CREDENTIALS_SEPARATOR = ":";
    public static final String PARAMS_VALUE_SEPARATOR = "=";
    public static final String PARAMS_SEPARATOR = "&";
    public static final String DOMAIN_NAME_SEPARATOR = ".";

    public abstract <T> T parse(String url) throws IOException, InvalidUrlException;

    public int getProtocolSegmentLength(String protocol) throws IOException, InvalidUrlException {
        int protocolLength = 0;

        if (!Strings.isNullOrEmpty(protocol)) {
            protocolLength = (protocol + PROTOCOL_COLON + PROTOCOL_SLASHES).length();
        }
        return protocolLength;
    }

    public int getCredentialsSegmentLength(String url, int credentialsIndexFrom) {
        int credentialsSymbolIndex = getIndex(url, CREDENTIALS_SYMBOL, credentialsIndexFrom);
        if (credentialsSymbolIndex != -1) {
            return url.substring(credentialsIndexFrom, credentialsSymbolIndex + 1).length();
        }

        return -1;
    }

    protected int getIndexOfDomainEndingSymbol(String url, int indexFrom) {
        int endOfDomainIndex = getIndex(url, END_OF_DOMAIN_SYMBOL, indexFrom);
        int queryParamIndex = getIndex(url, QUERY_PARAM_SYMBOL, indexFrom);
        int anchorIndex = getIndex(url, ANCHOR_SYMBOL, indexFrom);

        if (endOfDomainIndex != -1) {
            return endOfDomainIndex;
        } else if (queryParamIndex != -1) {
            return queryParamIndex;
        } else if (anchorIndex != -1) {
            return anchorIndex;
        }

        return -1;
    }

    public int getIndex(String url, String symbol) {
        return url.indexOf(symbol);
    }

    public int getIndex(String url, String symbol, int indexFrom) {
        return url.indexOf(symbol, indexFrom);
    }
}
