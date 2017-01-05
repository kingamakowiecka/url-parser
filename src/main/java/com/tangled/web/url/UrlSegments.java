package com.tangled.web.url;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlSegments {
    private String protocol;
    private String domain;
}
