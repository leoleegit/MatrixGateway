package com.matrix.service.entity;

import lombok.Data;

@Data
public class UserWithRole extends User{
    private int roleId;
    private String roleName;
}
