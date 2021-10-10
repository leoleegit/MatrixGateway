package com.matrix.admin.model.req;

import lombok.Data;

@Data
public class UserUpdateReq {
    private String nickname;

    private String telephone;

    private String email;

    private Integer sex;

    private String avatarUrl;

    private String currentPasswd;

    private String newPasswd;
}
