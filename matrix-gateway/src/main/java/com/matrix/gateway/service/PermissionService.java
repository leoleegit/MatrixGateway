package com.matrix.gateway.service;

import com.matrix.gateway.model.req.CheckAccessPermissionReq;
import com.matrix.gateway.model.resp.CheckAccessPermissionResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@Component
@FeignClient(name="matrix-admin")
public interface PermissionService {

    @PostMapping("admin/permission/check")
    public CheckAccessPermissionResp checkPermission(@RequestBody CheckAccessPermissionReq checkAccessPermissionReq, @RequestHeader("Authorization") String token);
}
