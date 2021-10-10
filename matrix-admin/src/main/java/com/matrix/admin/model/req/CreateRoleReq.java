package com.matrix.admin.model.req;

import lombok.Data;

@Data
public class CreateRoleReq {
    private String code;

    private String name;

    private String desc;
}
