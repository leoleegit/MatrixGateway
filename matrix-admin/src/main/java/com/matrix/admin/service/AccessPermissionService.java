package com.matrix.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.matrix.admin.model.req.PermissionQueryReq;
import com.matrix.admin.model.vo.PermissionTree;
import com.matrix.core.constants.CS;
import com.matrix.core.model.rest.Resp;
import com.matrix.service.entity.AccessPermission;
import com.matrix.service.impl.AccessPermissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class AccessPermissionService {

    @Autowired
    private AccessPermissionServiceImpl accessPermissionService;

    public Resp<Integer> insert(AccessPermission accessPermission){
        int re = accessPermissionService.insert(accessPermission);
        if(re>0){
            return Resp.success(re);
        }
        return Resp.fail("fail");
    }

    public Resp<String> updateById(AccessPermission accessPermission){
        int re = accessPermissionService.update(accessPermission);
        if(re>0){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public List<PermissionTree> list(){
        QueryWrapper<AccessPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("type", CS.PERMISSION_TREE.SYS, CS.PERMISSION_TREE.MENUS);
        return PermissionTree.getInstance(accessPermissionService.list(queryWrapper));
    }

    public List<AccessPermission> queryList(long userId){
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return accessPermissionService.queryList(queryWrapper);
    }

    public Resp<AccessPermission> queryListByRoleId(long roleId){
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        return Resp.success(accessPermissionService.queryListByRole(queryWrapper));
    }

    public Resp<AccessPermission> queryFunctionList(PermissionQueryReq queryReq, long userId){
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", CS.PERMISSION_TREE.FUNCTION)
                .eq("root_id",queryReq.getRootId())
                .eq(userId!=0,"user_id",userId);
        int count = accessPermissionService.queryCount(queryWrapper);
        PageHelper.startPage(queryReq.getCurrent(),queryReq.getPageSize());
        queryWrapper.orderBy(!StringUtils.isEmpty(queryReq.getSortField()),queryReq.isAsc(),queryReq.getSortField());
        return Resp.success(accessPermissionService.queryList(queryWrapper)).setCount(count);
    }

    public Resp<AccessPermission> info(int id){
        return Resp.success(accessPermissionService.info(id));
    }

    public Resp<AccessPermission> functions(int rootId){
        return Resp.success(accessPermissionService.functionList(rootId));
    }

    public Resp<String> deleteById(int id){
        int re = accessPermissionService.deleteById(id);
        if(re>0){
            return Resp.success();
        }
        return Resp.fail("fail");
    }
}
