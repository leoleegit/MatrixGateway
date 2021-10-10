package com.matrix.admin.model.req;

import com.matrix.core.model.rest.req.CommonQueryReq;
import lombok.Data;

@Data
public class PermissionQueryReq extends CommonQueryReq {
    private int rootId;
}
