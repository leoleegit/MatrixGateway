package com.matrix.admin.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAccessPermissionReq {

    @NotBlank(message="Name can't be blank")
    private String name;

    private Integer type;

    @NotBlank(message="Code can't be blank")
    private String code;

    private String desc;

    private Integer rootId;

    private String method;

    private String path;

    private Integer disable;

}
