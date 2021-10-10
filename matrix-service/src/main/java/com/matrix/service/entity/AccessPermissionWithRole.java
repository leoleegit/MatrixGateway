package com.matrix.service.entity;

import lombok.Data;

@Data
public class AccessPermissionWithRole extends AccessPermission{
    private Integer roleId;
}
