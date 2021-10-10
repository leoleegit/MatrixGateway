package com.matrix.admin.controller;

import com.matrix.admin.model.req.CreateAccessPermissionReq;
import com.matrix.admin.model.req.PermissionQueryReq;
import com.matrix.admin.model.req.UpdateAccessPermissionReq;
import com.matrix.admin.model.vo.PermissionTree;
import com.matrix.admin.service.AccessPermissionService;
import com.matrix.core.constants.ApiCode;
import com.matrix.core.constants.CS;
import com.matrix.core.controller.CommonCtrl;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.security.UserInfo;
import com.matrix.service.entity.AccessPermission;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class AccessPermissionController extends CommonCtrl {

    @Autowired
    private AccessPermissionService accessPermissionService;

    @PostMapping("delete")
    public Resp<String> deleteById(@RequestParam("id") int id){
        return accessPermissionService.deleteById(id);
    }

    @PostMapping("add")
    public Resp<Integer> add(@RequestBody CreateAccessPermissionReq createAccessPermissionReq){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        String role = userInfo.getRole();
        if(!CS.Role.SYSTEM_ADMIN.equals(role)){
            return Resp.fail(ApiCode.ERROR_403);
        }
        AccessPermission accessPermission = new AccessPermission();
        BeanUtils.copyProperties(createAccessPermissionReq,accessPermission);
        accessPermission.setCreatedBy(userInfo.getUsername());
        accessPermission.setUpdatedBy(userInfo.getUsername());
        //RequestMethod
        if(accessPermission.getMethod()==null){
            accessPermission.setMethod(CS.DEFAULT_REQUEST_METHOD);
        }
        if(accessPermission.getRootId()!=null){
            accessPermission.setType(CS.PERMISSION_TREE.MENUS);
        }else
            accessPermission.setType(CS.PERMISSION_TREE.SYS);
        accessPermission.setCode(accessPermission.getCode().toLowerCase());
        return accessPermissionService.insert(accessPermission);
    }

    @PostMapping("add/function")
    public Resp<Integer> addFunction(@RequestBody CreateAccessPermissionReq createAccessPermissionReq){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        String role = userInfo.getRole();
        if(!CS.Role.SYSTEM_ADMIN.equals(role)){
            return Resp.fail(ApiCode.ERROR_403);
        }
        if(createAccessPermissionReq.getRootId()==null){
            return Resp.fail("Function Root Id can't be null");
        }
        AccessPermission accessPermission = new AccessPermission();
        BeanUtils.copyProperties(createAccessPermissionReq,accessPermission);
        accessPermission.setCreatedBy(userInfo.getUsername());
        accessPermission.setUpdatedBy(userInfo.getUsername());
        //RequestMethod
        if(accessPermission.getMethod()==null){
            accessPermission.setMethod(CS.DEFAULT_REQUEST_METHOD);
        }
        accessPermission.setType(CS.PERMISSION_TREE.FUNCTION);
        accessPermission.setCode(accessPermission.getCode().toLowerCase());
        return accessPermissionService.insert(accessPermission);
    }

    @PutMapping("update")
    public Resp<String> updateById(@RequestBody UpdateAccessPermissionReq updateAccessPermissionReq){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        String role = userInfo.getRole();
        if(!CS.Role.SYSTEM_ADMIN.equals(role)){
            return Resp.fail(ApiCode.ERROR_403);
        }
        AccessPermission accessPermission = new AccessPermission();
        BeanUtils.copyProperties(updateAccessPermissionReq,accessPermission);
        accessPermission.setUpdatedBy(userInfo.getUsername());
        accessPermission.setUpdatedAt(new Date(System.currentTimeMillis()));
        //RequestMethod
        if(accessPermission.getMethod()==null){
            accessPermission.setMethod(CS.DEFAULT_REQUEST_METHOD);
        }
        accessPermission.setCode(accessPermission.getCode().toLowerCase());
        return accessPermissionService.updateById(accessPermission);
    }

    @GetMapping("list")
    public Resp<PermissionTree> list(){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        List<PermissionTree> list = accessPermissionService.list();
        if(!CS.Role.SYSTEM_ADMIN.equals(userInfo.getRole())){
            List<AccessPermission> accessPermissions = accessPermissionService.queryList(userInfo.getId());
            return Resp.success(PermissionTree.filter(list,accessPermissions));
        }
        return Resp.success(list);
    }

    @PostMapping("list/function")
    public Resp<AccessPermission> listFunction(@RequestBody PermissionQueryReq permissionQueryReq){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        if(!CS.Role.SYSTEM_ADMIN.equals(userInfo.getRole())){
            return accessPermissionService.queryFunctionList(permissionQueryReq,userInfo.getId());
        }
        return accessPermissionService.queryFunctionList(permissionQueryReq,0);
    }

    @GetMapping("info")
    public Resp<AccessPermission> info(@RequestParam("id") int id){
        String role = getCurrentUser().getUserInfo().getRole();
        if(!CS.Role.SYSTEM_ADMIN.equals(role)){
            return Resp.fail(ApiCode.ERROR_403);
        }
        return accessPermissionService.info(id);
    }

    @GetMapping("functions")
    public Resp<AccessPermission> functions(@RequestParam("root") int rootId){
        String role = getCurrentUser().getUserInfo().getRole();
        if(!CS.Role.SYSTEM_ADMIN.equals(role)){
            return Resp.fail(ApiCode.ERROR_403);
        }
        return accessPermissionService.functions(rootId);
    }
}
