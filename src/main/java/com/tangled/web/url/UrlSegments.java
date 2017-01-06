package com.tangled.web.url;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlSegments {
    private String protocol;
    private String domain;
    private UserCredentials credentials;
    private Map<String, String> params;
}
