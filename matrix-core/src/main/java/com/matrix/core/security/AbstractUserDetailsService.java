package com.matrix.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractUserDetailsService implements UserDetailsService {

    @Override
    public abstract UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
