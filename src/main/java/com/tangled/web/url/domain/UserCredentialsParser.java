package com.tangled.web.url.domain;

import java.io.IOException;

import com.tangled.web.url.UserCredentials;
import com.tangled.web.url.common.AbstractParser;
import com.tangled.web.url.protocol.InvalidProtocolException;

public class UserCredentialsParser extends AbstractParser {
    private int protocolSegmentLength;

    public UserCredentials parse(String url) throws IOException, InvalidProtocolException {
        int credentialsIndexFrom = protocolSegmentLength;
        int credentialsIndexTo = getIndex(url, CREDENTIALS_SYMBOL, credentialsIndexFrom);

        if (credentialsIndexTo > credentialsIndexFrom) {
            return buildUserCredentials(url, credentialsIndexFrom, credentialsIndexTo);
        }

        return null;
    }

    public UserCredentialsParser withProtocolSegmentLength(int protocolSegmentLength) {
        this.protocolSegmentLength = protocolSegmentLength;
        return this;
    }

    private UserCredentials buildUserCredentials(String url, int credentialsIndexFrom, int credentialsIndexTo) {
        String credentials = url.substring(credentialsIndexFrom, credentialsIndexTo);
        int separatorIndex = credentials.indexOf(CREDENTIALS_SEPARATOR);
        int passwordIndex = separatorIndex + 1;

        return UserCredentials.builder()
                .user(credentials.substring(0, separatorIndex))
                .password(credentials.substring(passwordIndex))
                .build();
    }
}
