package com.tangled.web.url.anchor;

import java.io.IOException;

import com.tangled.web.url.common.AbstractParser;
import com.tangled.web.url.common.InvalidUrlException;

public class AnchorParser extends AbstractParser<String> {

    @Override
    public String parse(String url) throws IOException, InvalidUrlException {
        int anchorIndexWithoutAnchorSymbol = url.indexOf(ANCHOR_SYMBOL) + 1;

        return url.substring(anchorIndexWithoutAnchorSymbol);
    }
}
