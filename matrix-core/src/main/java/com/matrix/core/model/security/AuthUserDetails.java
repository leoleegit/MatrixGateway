package com.matrix.core.model.security;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AuthUserDetails implements UserDetails {

    private UserInfo userInfo;

    private String credential;

    private String cacheKey;

    private String loginIp;

    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public AuthUserDetails() {
    }

    public AuthUserDetails(UserInfo userInfo, String credential) {
        this.userInfo = userInfo;
        this.credential = credential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return credential;
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static AuthUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        try {
            return (AuthUserDetails) authentication.getPrincipal();
        }catch (Exception e) {
            return null;
        }
    }
}
