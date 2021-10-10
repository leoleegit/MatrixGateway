package com.matrix.core.model.rest.req;

import lombok.Data;

@Data
public class AuthenticationReq {
    private int authType;
    private String username;
    private String password;
    private String verCode;
    private String codeID;

    public AuthenticationReq() {
    }

    public AuthenticationReq(String username, String password, String verCode, String codeID) {
        this.username = username;
        this.password = password;
        this.verCode = verCode;
        this.codeID  = codeID;
    }
}
