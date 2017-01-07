package com.tangled.web.url.common;

import java.io.IOException;

import com.google.common.base.Strings;

public abstract class AbstractParser<T> {
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

    public abstract T parse(String url) throws IOException, InvalidUrlException;

    public int getProtocolSegmentLength(String protocol) throws InvalidUrlException {
        int protocolLength = 0;

        if (!Strings.isNullOrEmpty(protocol)) {
            protocolLength = (protocol + PROTOCOL_COLON + PROTOCOL_SLASHES).length();
        }
        return protocolLength;
    }

    public int getCredentialsSegmentLength(String url, int credentialsIndexFrom) {
        int credentialsSymbolIndex = url.indexOf(CREDENTIALS_SYMBOL, credentialsIndexFrom);
        if (credentialsSymbolIndex != -1) {
            return url.substring(credentialsIndexFrom, credentialsSymbolIndex + 1).length();
        }

        return -1;
    }

    protected int getIndexOfAnchorOrParamsSymbol(String url, int indexFrom) {
        int queryParamIndex = url.indexOf(QUERY_PARAM_SYMBOL, indexFrom);
        int anchorIndex = url.indexOf(ANCHOR_SYMBOL, indexFrom);

        if (queryParamIndex != -1) {
            return queryParamIndex;
        } else if (anchorIndex != -1) {
            return anchorIndex;
        }

        return -1;
    }
}
