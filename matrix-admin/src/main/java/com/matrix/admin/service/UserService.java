package com.matrix.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.matrix.admin.model.req.UserQueryReq;
import com.matrix.admin.model.req.UserUpdateReq;
import com.matrix.core.constants.CS;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.security.UserInfo;
import com.matrix.service.entity.User;
import com.matrix.service.entity.UserWithRole;
import com.matrix.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserServiceImpl userService;

    public boolean exist(String username, int authType){
        User user = userService.selectByLogin(username, authType);
        return user!=null;
    }

    public User userid(long id){
        return userService.getById(id);
    }

    public Resp<UserInfo> user(String username){
        User user = userService.selectByLogin(username, CS.AuthType.USER_NAME);
        if(user!=null){
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user,userInfo);
            return Resp.success(userInfo);
        }
        return Resp.fail("User not be found");
    }

    public UserInfo userInfo(String username){
        User user = userService.selectByLogin(username, CS.AuthType.USER_NAME);
        if(user!=null){
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user,userInfo);
            return userInfo;
        }
        return null;
    }

    public Resp<String> resetPassword(long userid, String password){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password",password).set("updated_at",new Date(System.currentTimeMillis()));
        updateWrapper.eq("id",userid);
        boolean re = userService.update(updateWrapper);
        if(re){
            return Resp.success();
        }else{
            return Resp.fail();
        }
    }

    public Resp<UserWithRole> list(String role,String createdBy, UserQueryReq queryReq){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role).eq("created_by",createdBy).eq(!StringUtils.isEmpty(queryReq.getRoleName()),"role_name",queryReq.getRoleName())
                .like(!StringUtils.isEmpty(queryReq.getTelephone()),"telephone",queryReq.getTelephone())
                .like(!StringUtils.isEmpty(queryReq.getEmail()),"email",queryReq.getEmail());
        int count = userService.queryCount(queryWrapper);
        PageHelper.startPage(queryReq.getCurrent(),queryReq.getPageSize());
        queryWrapper.orderBy(!StringUtils.isEmpty(queryReq.getSortField()),queryReq.isAsc(),queryReq.getSortField());
        List<UserWithRole> list = userService.queryList(queryWrapper);
        return Resp.success(list).setCount(count);
    }

    public Resp<String> insertUser(User user){
        int re = userService.insert(user);
        if(re>0){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public Resp<String> update(long userid,UserUpdateReq req) {
         UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
         updateWrapper
                 .set(!StringUtils.isEmpty(req.getNickname()),"nickname",req.getNickname())
                 .set(!StringUtils.isEmpty(req.getTelephone()),"telephone",req.getTelephone())
                 .set(!StringUtils.isEmpty(req.getEmail()),"email",req.getEmail())
                 .set(!StringUtils.isEmpty(req.getAvatarUrl()),"avatar_url",req.getAvatarUrl())
                 .eq("id",userid);
         boolean re = userService.update(updateWrapper);
        if(re){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

    public Resp<String> updatePasswd(long userid, String newPasswd) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .set("password",newPasswd)
                .eq("id",userid);
        boolean re = userService.update(updateWrapper);
        if(re){
            return Resp.success();
        }
        return Resp.fail("fail");
    }

}
