package com.tangled.web.url;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCredentials {
    private String user;
    private String password;
}
