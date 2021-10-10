package com.matrix.admin.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.matrix.admin.model.req.UserQueryReq;
import com.matrix.admin.model.req.UserUpdateReq;
import com.matrix.admin.model.resp.CurrentUserResp;
import com.matrix.admin.service.AccessPermissionService;
import com.matrix.admin.service.UserService;
import com.matrix.core.config.DefaultConfig;
import com.matrix.core.constants.ApiCode;
import com.matrix.core.constants.CS;
import com.matrix.core.controller.CommonCtrl;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.security.UserInfo;
import com.matrix.core.util.AuthUtil;
import com.matrix.service.entity.AccessPermission;
import com.matrix.service.entity.User;
import com.matrix.service.entity.UserWithRole;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends CommonCtrl {
    @Autowired
    private DefaultConfig defaultConfig;

    @Autowired
    private UserService userService;
    @Autowired
    private AccessPermissionService accessPermissionService;

    @GetMapping("info")
    public Resp<UserInfo> user(@RequestParam("username") String username){
        return userService.user(username);
    }

    @GetMapping("me")
    public Resp<UserInfo> me(){
        return userService.user(getCurrentUser().getUsername());
    }

    @GetMapping("current")
    public Resp<CurrentUserResp> current(){
        CurrentUserResp currentUserResp = new CurrentUserResp();
        UserInfo userInfo = userService.userInfo(getCurrentUser().getUsername());
        currentUserResp.setUser(userInfo);

        List<AccessPermission> accessPermissionList = accessPermissionService.queryList(userInfo.getId());
        if(accessPermissionList!=null && accessPermissionList.size()>0){
            List<String> permissions = new ArrayList<>(accessPermissionList.size());
            for (AccessPermission accessPermission: accessPermissionList) {
                permissions.add(accessPermission.getCode());
            }
            currentUserResp.setPermissions(permissions.toArray(new String[permissions.size()]));
        }

        return Resp.success(currentUserResp);
    }

    @PostMapping("resetPassword")
    public Resp<String> resetPassword(@RequestParam("id") long id){
        User user = userService.userid(id);
        if(user==null)return Resp.fail("User not been found");
        UserInfo userInfo = getCurrentUser().getUserInfo();
        if(!user.getCreatedBy().equals(userInfo.getUsername())){
            return Resp.fail(ApiCode.ERROR_403);
        }
        String password = AuthUtil.passwordEncoder(SecureUtil.md5(defaultConfig.getPassword()));
        return userService.resetPassword(id,password);
    }

    @PostMapping("update")
    public Resp<String> update(@RequestBody UserUpdateReq req){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        return userService.update(userInfo.getId(),req);
    }

    @PostMapping("update/password")
    public Resp<String> updatePasswd(@RequestBody UserUpdateReq req){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        User user = userService.userid(userInfo.getId());
        if(AuthUtil.matches(req.getCurrentPasswd(),user.getPassword())){
            if(StringUtils.isEmpty(req.getNewPasswd()))
                return Resp.fail("New password can't be empty");
            return userService.updatePasswd(userInfo.getId(),AuthUtil.passwordEncoder(req.getNewPasswd()));
        }

        return Resp.fail("Current password is error");
    }

    @PostMapping("list")
    public Resp<UserWithRole> list(@RequestBody UserQueryReq queryReq){
        String role = getCurrentUser().getUserInfo().getRole();
        if(CS.Role.SYSTEM_ADMIN.equals(role)){
            role = CS.Role.USER_ADMIN;
        }else if(CS.Role.USER_ADMIN.equals(role)){
            role = CS.Role.USER;
        }else
            Resp.fail(ApiCode.ERROR_403);
        return userService.list(role,getCurrentUser().getUsername(),queryReq);
    }

    @PostMapping("create")
    public Resp<String> add(@RequestBody User user){
        UserInfo userInfo = getCurrentUser().getUserInfo();
        String role = userInfo.getRole();
        if(CS.Role.SYSTEM_ADMIN.equals(role)){
            role = CS.Role.USER_ADMIN;
        }else if(CS.Role.USER_ADMIN.equals(role)){
            role = CS.Role.USER;
        }else
            Resp.fail(ApiCode.ERROR_403);
        if(user.getNickname()==null){
            user.setNickname(user.getUsername());
        }
        if(userService.exist(user.getUsername(),CS.AuthType.USER_NAME)){
            return Resp.fail("Username have been exist");
        }
        if(StrUtil.isNotEmpty(user.getTelephone()) && userService.exist(user.getTelephone(),CS.AuthType.PHONE)){
            return Resp.fail("Telephone have been exist");
        }
        if(StrUtil.isNotEmpty(user.getEmail()) && userService.exist(user.getEmail(),CS.AuthType.EMAIL)){
            return Resp.fail("Email have been exist");
        }

        user.setRole(role);
        user.setCreatedBy(userInfo.getUsername());
        user.setUpdatedBy(userInfo.getUsername());
        user.setAvatarUrl(defaultConfig.getAvatarUrl());
        user.setPassword(AuthUtil.passwordEncoder(SecureUtil.md5(defaultConfig.getPassword())));
        return userService.insertUser(user);
    }

}
