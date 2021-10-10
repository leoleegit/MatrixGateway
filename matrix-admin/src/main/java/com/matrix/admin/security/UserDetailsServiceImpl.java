package com.matrix.admin.security;

import com.matrix.core.constants.ApiCode;
import com.matrix.core.model.security.AuthUserDetails;
import com.matrix.core.model.security.UserInfo;
import com.matrix.core.security.AbstractUserDetailsService;
import com.matrix.service.entity.User;
import com.matrix.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl extends AbstractUserDetailsService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s.indexOf(":")!=-1){
            int authType   = Integer.parseInt(s.split(":")[0]);
            String username= s.split(":")[1];
            User user = userService.selectByLogin(username, authType);
            if(user!=null && user.getDisable()!=1){
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(user,userInfo);
                return new AuthUserDetails(userInfo,user.getPassword());
            }
        }
        throw  new UsernameNotFoundException(ApiCode.ERROR_1002.status);
    }
}
