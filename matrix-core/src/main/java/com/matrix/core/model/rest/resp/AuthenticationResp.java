package com.matrix.core.model.rest.resp;

import com.matrix.core.model.security.UserInfo;
import lombok.Data;

import java.util.Date;

@Data
public class AuthenticationResp {
    private Date createdAt;
    private Date expiresIn;
    private String token;
    private UserInfo user;
    private String[] permissions;
}
