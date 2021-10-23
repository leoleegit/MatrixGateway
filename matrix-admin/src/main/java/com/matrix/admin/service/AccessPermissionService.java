package com.matrix.admin.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.google.gson.reflect.TypeToken;
import com.matrix.admin.model.req.CheckAccessPermissionReq;
import com.matrix.admin.model.req.PermissionQueryReq;
import com.matrix.admin.model.resp.CheckAccessPermissionResp;
import com.matrix.admin.model.vo.PermissionTree;
import com.matrix.core.constants.CS;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.util.RedisUtil;
import com.matrix.service.entity.AccessPermission;
import com.matrix.service.impl.AccessPermissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccessPermissionService {

    @Autowired
    private AccessPermissionServiceImpl accessPermissionService;

    public CheckAccessPermissionResp check(long userId, CheckAccessPermissionReq checkAccessPermissionReq){
        CheckAccessPermissionResp checkAccessPermissionResp = new CheckAccessPermissionResp();
        List<AccessPermission> allAccessPermissions = allAccessPermission();
        // 判断当前访问资源是否有权限控制
        List<AccessPermission> matchPermission = allAccessPermissions.stream().filter(permissionInfo -> {
            String uri = permissionInfo.getPath();
            String endEx = uri.endsWith("{*}")?"":"$";
            if (uri.indexOf("{") > 0) {
                uri = uri.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
            }

            String regEx = StrUtil.format("^{}{}",uri,endEx);
            return (Pattern.compile(regEx).matcher(checkAccessPermissionReq.getRequestUrl()).find())
                    && (
                        checkAccessPermissionReq.getMethod().equals(permissionInfo.getMethod()) ||
                        CS.DEFAULT_REQUEST_METHOD.equals(permissionInfo.getMethod())
                    );
        }).collect(Collectors.toList());
        //资源没有限制
        if (matchPermission.size() == 0) {
            checkAccessPermissionResp.setIsAuth(true);
            return checkAccessPermissionResp;
        }
        List<AccessPermission> userAccessPermissions = userAccessPermission(userId);
        AccessPermission current = null;
        for (AccessPermission info : userAccessPermissions) {
            boolean anyMatch = matchPermission.stream().anyMatch(permissionInfo -> permissionInfo.getCode().equals(info.getCode()));
            if (anyMatch) {
                current = info;
                break;
            }
        }
        if (current == null) {
            // 当前用户不拥有该权限
            checkAccessPermissionResp.setIsAuth(false);
        } else {
            // 当前用户拥有该资源的访问权限
            BeanUtils.copyProperties(current,checkAccessPermissionResp);
            checkAccessPermissionResp.setIsAuth(true);
        }

        return checkAccessPermissionResp;
    }

    private List<AccessPermission> allAccessPermission(){
        List<AccessPermission> list;
        Type type =new TypeToken<List<AccessPermission>>() {}.getType();
        list = RedisUtil.getObject(CS.CacheKey.All_PERMISSION,type);
        if(list!=null)
            return list;

        QueryWrapper<AccessPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("path");
        list = accessPermissionService.list(queryWrapper);

        RedisUtil.setObject(CS.CacheKey.All_PERMISSION,list,12, TimeUnit.HOURS);
        return list;
    }

    private List<AccessPermission> userAccessPermission(long userId){
        List<AccessPermission> list;
        Type type =new TypeToken<List<AccessPermission>>() {}.getType();
        list = RedisUtil.getObject(CS.cacheKey(CS.CacheKey.USER_PERMISSION,String.valueOf(userId)),type);
        if(list!=null)
            return list;

        QueryWrapper<AccessPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        list = accessPermissionService.queryList(queryWrapper);

        RedisUtil.setObject(CS.cacheKey(CS.CacheKey.USER_PERMISSION,String.valueOf(userId)),list,12, TimeUnit.HOURS);
        return list;
    }

    public Resp<Integer> insert(AccessPermission accessPermission){
        int re = accessPermissionService.insert(accessPermission);
        if(re>0){
            RedisUtil.del(CS.CacheKey.All_PERMISSION);
            RedisUtil.delByPrefix(CS.cachePrefix(CS.CacheKey.USER_PERMISSION));
            return Resp.success(re);
        }
        return Resp.fail("fail");
    }

    public Resp<String> updateById(AccessPermission accessPermission){
        int re = accessPermissionService.update(accessPermission);
        if(re>0){
            RedisUtil.del(CS.CacheKey.All_PERMISSION);
            RedisUtil.delByPrefix(CS.cachePrefix(CS.CacheKey.USER_PERMISSION));
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public Resp<String> deleteById(int id){
        int re = accessPermissionService.deleteById(id);
        if(re>0){
            RedisUtil.del(CS.CacheKey.All_PERMISSION);
            RedisUtil.delByPrefix(CS.cachePrefix(CS.CacheKey.USER_PERMISSION));
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
}
