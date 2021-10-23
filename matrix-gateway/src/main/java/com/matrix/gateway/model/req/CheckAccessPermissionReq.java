package com.matrix.gateway.model.req;

import lombok.Data;

@Data
public class CheckAccessPermissionReq {
    private String method;
    private String requestUrl;

    public CheckAccessPermissionReq(String method, String requestUrl) {
        this.method = method;
        this.requestUrl = requestUrl;
    }
}
