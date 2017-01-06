package com.tangled.web.url.common;

public class SymbolIndexHelper {

    public static int getIndex(String url, String symbol) {
        return url.indexOf(symbol);
    }

    public static int getIndex(String url, String symbol, int indexFrom) {
        return url.indexOf(symbol, indexFrom);
    }
}
