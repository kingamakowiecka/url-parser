package com.tangled.web.url.protocol;

import java.io.IOException;
import java.util.List;

import com.tangled.web.exception.EmptyProtocolException;
import com.tangled.web.exception.InvalidProtocolException;

public class ProtocolParser {

    public static String parse(String url) throws IOException, InvalidProtocolException {
        try {
            return getUrlProtocol(url);
        } catch (EmptyProtocolException e) {
            return null;
        }
    }

    private static String getUrlProtocol(String url) throws EmptyProtocolException, IOException, InvalidProtocolException {
        String urlProtocol = url.substring(0, getUrlColonIndex(url));
        if (ProtocolSchemas.getInstance().getUriSchemas().contains(urlProtocol)) {
            return urlProtocol;
        }
        throw new InvalidProtocolException();
    }

    private static int getUrlColonIndex(String url) throws EmptyProtocolException {
        int colonIndex = url.indexOf(":");

        if (colonIndex == -1) {
            throw new EmptyProtocolException();
        }

        return colonIndex;
    }
}
