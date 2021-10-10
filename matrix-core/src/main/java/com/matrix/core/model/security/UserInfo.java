package com.matrix.core.model.security;

import lombok.Data;

@Data
public class UserInfo {
    private Long id;

    private String username;

    private String nickname;

    private String telephone;

    private String email;

    private Integer sex;

    private String avatarUrl;

    private String role;
}
