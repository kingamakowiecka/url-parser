package com.tangled.web.url.param;

import static com.tangled.web.url.common.Statics.ANCHOR_SYMBOL;
import static com.tangled.web.url.common.Statics.PARAMS_SEPARATOR;
import static com.tangled.web.url.common.Statics.PARAMS_VALUE_SEPARATOR;
import static com.tangled.web.url.common.Statics.QUERY_PARAM_SYMBOL;
import static com.tangled.web.url.common.SymbolIndexHelper.getIndex;

import java.util.Map;

import com.google.common.base.Splitter;

public class ParamParser {

    public static Map<String, String> parse(String url) throws InvalidParamException {
        try {
            String queryParams = getQueryParamsString(url, getQueryParamIndex(url), getAnchorIndex(url));
            return getParamsMap(queryParams);
        } catch (EmptyParamException e) {
            return null;
        }
    }

    private static Map<String, String> getParamsMap(String queryParams) throws InvalidParamException {
        try {
            Map<String, String> paramsMap = Splitter.on(PARAMS_SEPARATOR)
                    .withKeyValueSeparator(PARAMS_VALUE_SEPARATOR)
                    .split(queryParams);
            return paramsMap;
        } catch (IllegalArgumentException ex) {
            throw new InvalidParamException();
        }
    }

    private static int getAnchorIndex(String url) {
        return getIndex(url, ANCHOR_SYMBOL);
    }

    private static String getQueryParamsString(String url, int queryParamIndex, int anchorIndex) {
        int queryParamsIndexWithoutQuestionMark = queryParamIndex + 1;
        return anchorIndex != -1 ?
                url.substring(queryParamsIndexWithoutQuestionMark, anchorIndex) :
                url.substring(queryParamsIndexWithoutQuestionMark);
    }

    private static int getQueryParamIndex(String url) throws EmptyParamException {
        int queryParamIndex = getIndex(url, QUERY_PARAM_SYMBOL);
        if (queryParamIndex == -1) {
            throw new EmptyParamException();
        }

        return queryParamIndex;
    }
}
