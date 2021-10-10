package com.matrix.media.security;

import com.matrix.core.constants.ApiCode;
import com.matrix.core.security.AbstractUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl extends AbstractUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        throw  new UsernameNotFoundException(ApiCode.ERROR_1002.status);
    }
}
