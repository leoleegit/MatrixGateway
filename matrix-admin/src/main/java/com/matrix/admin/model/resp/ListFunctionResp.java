package com.matrix.admin.model.resp;

import com.matrix.service.entity.AccessPermission;
import lombok.Data;

@Data
public class ListFunctionResp extends AccessPermission {
    private Integer selected;
}
