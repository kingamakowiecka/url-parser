package com.tangled.web.url.protocol;

import static com.tangled.web.url.common.Statics.CREDENTIALS_SYMBOL;
import static com.tangled.web.url.common.Statics.PROTOCOL_COLON;
import static com.tangled.web.url.common.Statics.PROTOCOL_SLASHES;
import static com.tangled.web.url.common.SymbolIndexHelper.getIndex;

import java.io.IOException;

import com.google.common.base.Strings;

public class ProtocolParser {

    public static String parse(String url) throws IOException, InvalidProtocolException {
        try {
            return getUrlProtocol(url);
        } catch (EmptyProtocolException e) {
            return null;
        }
    }

    public static int getProtocolSegmentLength(String url) throws IOException, InvalidProtocolException {
        int protocolLength = 0;
        String protocol = parse(url);

        if (!Strings.isNullOrEmpty(protocol)) {
            protocolLength = (protocol + PROTOCOL_COLON + PROTOCOL_SLASHES).length();
        }
        return protocolLength;
    }

    private static String getUrlProtocol(String url) throws EmptyProtocolException, IOException, InvalidProtocolException {
        int urlColonIndex = getUrlColonIndex(url);
        String urlProtocol = url.substring(0, urlColonIndex).toLowerCase();
        if (!isProtocolOnSchemasList(urlProtocol) || !hasProtocolTwoSlashes(url, urlColonIndex)) {
            verifyEmptyProtocolAndUserCredentials(url, urlColonIndex);
            throw new InvalidProtocolException();
        }

        return urlProtocol;
    }

    private static boolean isProtocolOnSchemasList(String urlProtocol) throws IOException {
        return ProtocolSchemas.getInstance().getUriSchemas().contains(urlProtocol);
    }

    private static boolean hasProtocolTwoSlashes(String url, int urlColonIndex) {
        return url.substring(urlColonIndex + 1, urlColonIndex + 3).equals(PROTOCOL_SLASHES);
    }

    private static int getUrlColonIndex(String url) throws EmptyProtocolException {
        int colonIndex = getIndex(url, PROTOCOL_COLON);

        if (colonIndex == -1) {
            throw new EmptyProtocolException();
        }

        return colonIndex;
    }

    private static void verifyEmptyProtocolAndUserCredentials(String url, int urlColonIndex) throws EmptyProtocolException {
        int credentialsSymbolIndex = getIndex(url, CREDENTIALS_SYMBOL);

        if (credentialsSymbolIndex != -1 && credentialsSymbolIndex > getIndex(url, PROTOCOL_COLON, urlColonIndex)) {
            throw new EmptyProtocolException();
        }
    }
}
