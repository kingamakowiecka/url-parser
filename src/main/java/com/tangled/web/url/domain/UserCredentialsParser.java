package com.tangled.web.url.domain;

import static com.tangled.web.url.common.SymbolIndexHelper.getIndex;
import static com.tangled.web.url.protocol.ProtocolParser.getProtocolSegmentLength;

import java.io.IOException;

import com.tangled.web.url.common.Statics;
import com.tangled.web.url.UserCredentials;
import com.tangled.web.url.protocol.InvalidProtocolException;

public class UserCredentialsParser {

    public static UserCredentials parseUserCredentials(String url) throws IOException, InvalidProtocolException {
        int credentialsIndexFrom = getProtocolSegmentLength(url);
        int credentialsIndexTo = getCredentialsSymbolIndex(url, credentialsIndexFrom);

        if (credentialsIndexTo > credentialsIndexFrom) {
            return buildUserCredentials(url, credentialsIndexFrom, credentialsIndexTo);
        }

        return null;
    }

    public static int getCredentialsSegmentLength(String url, int credentialsIndexFrom) {
        int credentialsSymbolIndex = getCredentialsSymbolIndex(url, credentialsIndexFrom);
        if (credentialsSymbolIndex >= 0) {
            return url.substring(credentialsIndexFrom, credentialsSymbolIndex + 1).length();
        }

        return 0;
    }

    private static UserCredentials buildUserCredentials(String url, int credentialsIndexFrom, int credentialsIndexTo) {
        String credentials = url.substring(credentialsIndexFrom, credentialsIndexTo);
        int separatorIndex = credentials.indexOf(Statics.CREDENTIALS_SEPARATOR);

        return UserCredentials.builder()
                .user(credentials.substring(0, separatorIndex))
                .password(credentials.substring(separatorIndex + 1))
                .build();
    }

    private static int getCredentialsSymbolIndex(String url, int credentialsIndexFrom) {
        return getIndex(url, Statics.CREDENTIALS_SYMBOL, credentialsIndexFrom);
    }
}
