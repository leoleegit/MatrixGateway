package com.matrix.admin.model.req;

import com.matrix.core.model.rest.req.CommonQueryReq;
import lombok.Data;

@Data
public class UserQueryReq extends CommonQueryReq {
    private String telephone;
    private String email;
    private String roleName;
}
