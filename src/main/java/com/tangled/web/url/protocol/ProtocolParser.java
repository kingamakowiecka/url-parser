package com.tangled.web.url.protocol;

import java.io.IOException;

import com.tangled.web.url.common.AbstractParser;

public class ProtocolParser extends AbstractParser {

    public String parse(String url) throws IOException, InvalidProtocolException {
        try {
            return getUrlProtocol(url);
        } catch (EmptyProtocolException e) {
            return null;
        }
    }

    private String getUrlProtocol(String url) throws EmptyProtocolException, IOException, InvalidProtocolException {
        int urlColonIndex = getUrlColonIndex(url);
        String urlProtocol = url.substring(0, urlColonIndex).toLowerCase();
        if (!isProtocolOnSchemasList(urlProtocol) || !hasProtocolTwoSlashes(url, urlColonIndex)) {
            verifyEmptyProtocolAndUserCredentials(url, urlColonIndex);
            throw new InvalidProtocolException();
        }

        return urlProtocol;
    }

    private boolean isProtocolOnSchemasList(String urlProtocol) throws IOException {
        return ProtocolSchemas.getInstance().getUriSchemas().contains(urlProtocol);
    }

    private boolean hasProtocolTwoSlashes(String url, int urlColonIndex) {
        return url.substring(urlColonIndex + 1, urlColonIndex + 3).equals(PROTOCOL_SLASHES);
    }

    private int getUrlColonIndex(String url) throws EmptyProtocolException {
        int colonIndex = url.indexOf(PROTOCOL_COLON);

        if (colonIndex == -1) {
            throw new EmptyProtocolException();
        }

        return colonIndex;
    }

    private void verifyEmptyProtocolAndUserCredentials(String url, int urlColonIndex) throws EmptyProtocolException {
        int credentialsSymbolIndex = url.indexOf(CREDENTIALS_SYMBOL);

        if (credentialsSymbolIndex != -1 && credentialsSymbolIndex > url.indexOf(PROTOCOL_COLON, urlColonIndex)) {
            throw new EmptyProtocolException();
        }
    }
}
