package com.matrix.admin.model.resp;

import lombok.Data;

@Data
public class CheckAccessPermissionResp {
    private Boolean isAuth;
    private String name;
    private String code;
    private String method;
    private String path;
}
