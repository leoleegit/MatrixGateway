package com.matrix.admin.model.req;

import lombok.Data;

@Data
public class CheckAccessPermissionReq {
    private String method;
    private String requestUrl;
}
