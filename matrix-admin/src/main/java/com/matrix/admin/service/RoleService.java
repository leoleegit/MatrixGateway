package com.matrix.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.matrix.admin.model.req.CreateRolePermissionReq;
import com.matrix.core.constants.CS;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.util.RedisUtil;
import com.matrix.service.entity.Role;
import com.matrix.service.entity.RolePermission;
import com.matrix.service.entity.RoleUser;
import com.matrix.service.impl.RolePermissionServiceImpl;
import com.matrix.service.impl.RoleServiceImpl;
import com.matrix.service.impl.RoleUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private RolePermissionServiceImpl rolePermissionService;

    @Autowired
    private RoleUserServiceImpl roleUserService;

    public Resp<Integer> insert(Role role){
        int re = roleService.insert(role);
        if(re>0){
            return Resp.success(re);
        }
        return Resp.fail("fail");
    }

    public Resp<String> updateById(Role role){
        int re = roleService.update(role);
        if(re>0){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public Resp<Role> selectListByCreatedBy(String createdBy){
        return Resp.success(roleService.selectListByCreatedBy(createdBy));
    }

    public Resp<Role> info(int id){
        return Resp.success(roleService.info(id));
    }

    public Resp<String> deleteById(int id){
        int re = roleService.deleteById(id);
        if(re>0){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public Resp<RolePermission> selectPermissionByRoleId(int roleId){
        return Resp.success(rolePermissionService.selectByRoleId(roleId));
    }

    public Resp<Integer> insertRolePermission(RolePermission rolePermission){
        int re = rolePermissionService.insert(rolePermission);
        if(re>0){
            RedisUtil.delByPrefix(CS.cachePrefix(CS.CacheKey.USER_PERMISSION));
            return Resp.success(re);
        }
        return Resp.fail("fail");
    }

    public Resp<String> insertRoleUser(RoleUser roleUser){
        UpdateWrapper<RoleUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role_id",roleUser.getRoleId());
        updateWrapper.eq("user_id",roleUser.getUserId());
        boolean re = roleUserService.saveOrUpdate(roleUser,updateWrapper);
        if(re){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public Resp<String>  createRolePermission(CreateRolePermissionReq createRolePermissionReq){
        int roleId = createRolePermissionReq.getRoleId();
        if(roleId==0)return Resp.fail("role id can't be null");
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("`type`", CS.PERMISSION_TREE.SYS, CS.PERMISSION_TREE.MENUS)
                .eq("role_id",createRolePermissionReq.getRoleId());
        rolePermissionService.deleteByQuery(queryWrapper);
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        for(int id : createRolePermissionReq.getIds()){
            rolePermission.setPermissionId(id);
            rolePermissionService.insert(rolePermission);
        }
        RedisUtil.delByPrefix(CS.cachePrefix(CS.CacheKey.USER_PERMISSION));
        return Resp.success();
    }

    public Resp<String>  createRolePermissionFunction(CreateRolePermissionReq createRolePermissionReq){
        int roleId = createRolePermissionReq.getRoleId();
        if(roleId==0)return Resp.fail("role id can't be null");
        int rootId = createRolePermissionReq.getRootId();
        if(rootId==0)return Resp.fail("root id can't be null");
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`type`", CS.PERMISSION_TREE.FUNCTION)
                .eq("role_id",createRolePermissionReq.getRoleId())
                .eq("root_id",createRolePermissionReq.getRootId());
        rolePermissionService.deleteByQuery(queryWrapper);
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        for(int id : createRolePermissionReq.getIds()){
            rolePermission.setPermissionId(id);
            rolePermissionService.insert(rolePermission);
        }
        RedisUtil.delByPrefix(CS.cachePrefix(CS.CacheKey.USER_PERMISSION));
        return Resp.success();
    }
}
