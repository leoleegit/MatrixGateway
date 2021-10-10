package com.matrix.admin.model.req;

import lombok.Data;

@Data
public class CreateRoleUserReq {
    private int userId;
    private int roleId;
}
