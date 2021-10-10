package com.matrix.admin.controller;

import com.matrix.admin.model.req.CreateRolePermissionReq;
import com.matrix.admin.model.req.CreateRoleReq;
import com.matrix.admin.model.req.CreateRoleUserReq;
import com.matrix.admin.model.req.UpdateRoleReq;
import com.matrix.admin.service.AccessPermissionService;
import com.matrix.admin.service.RoleService;
import com.matrix.core.controller.CommonCtrl;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.security.UserInfo;
import com.matrix.service.entity.AccessPermission;
import com.matrix.service.entity.Role;
import com.matrix.service.entity.RolePermission;
import com.matrix.service.entity.RoleUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/role")
public class RoleController extends CommonCtrl {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AccessPermissionService accessPermissionService;

    @PostMapping("permission/add")
    public Resp<Integer> add(@RequestBody RolePermission rolePermission){
        return roleService.insertRolePermission(rolePermission);
    }

    @PostMapping("permission/create")
    public Resp<String> createRolePermission(@RequestBody CreateRolePermissionReq createRolePermissionReq){
        return roleService.createRolePermission(createRolePermissionReq);
    }

    @PostMapping("permission/function/create")
    public Resp<String> createRolePermissionFunction(@RequestBody CreateRolePermissionReq createRolePermissionReq){
        return roleService.createRolePermissionFunction(createRolePermissionReq);
    }

    @GetMapping("permission/list")
    public Resp<AccessPermission> list(@RequestParam(name="roleId") Integer roleId){
        return accessPermissionService.queryListByRoleId(roleId);
    }

    @PostMapping("add")
    public Resp<Integer> add(@RequestBody CreateRoleReq createRoleReq){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        Role role = new Role();
        BeanUtils.copyProperties(createRoleReq,role);
        role.setCreatedBy(userInfo.getUsername());
        role.setUpdatedBy(userInfo.getUsername());
        return roleService.insert(role);
    }

    @PostMapping("user/add")
    public Resp<String> createRoleUser(@RequestBody CreateRoleUserReq createRoleUserReq){
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(createRoleUserReq.getRoleId());
        roleUser.setUserId(createRoleUserReq.getUserId());
        return roleService.insertRoleUser(roleUser);
    }

    @PutMapping("update")
    public Resp<String> updateById(@RequestBody UpdateRoleReq updateRoleReq){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        Role role = new Role();
        BeanUtils.copyProperties(updateRoleReq,role);
        role.setUpdatedBy(userInfo.getUsername());
        role.setUpdatedAt(new Date(System.currentTimeMillis()));
        return roleService.updateById(role);
    }

    @GetMapping("list")
    public Resp<Role> list(){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        return roleService.selectListByCreatedBy(userInfo.getUsername());
    }

    @GetMapping("info")
    public Resp<Role> info(@RequestParam("id") int id){
        return roleService.info(id);
    }

}
