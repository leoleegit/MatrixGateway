package com.matrix.admin.model.resp;

import com.matrix.core.model.security.UserInfo;
import lombok.Data;

@Data
public class CurrentUserResp {
    private UserInfo user;
    private String[] permissions;
}
