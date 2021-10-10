package com.matrix.admin.model.req;

import lombok.Data;

@Data
public class CreateRolePermissionReq {
    private int[] ids;
    private int roleId;
    private int rootId;
}
