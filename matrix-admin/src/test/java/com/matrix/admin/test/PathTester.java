package com.matrix.admin.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class PathTester {
    public static void main(String[] args){
        path();
    }

    public static void password(){
        String password = "p@ssw0rd";
        password =  cn.hutool.crypto.SecureUtil.md5(password);
        System.out.println(password);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String passHash = bCryptPasswordEncoder.encode(password);
        System.out.println(passHash);
        final boolean matches =  bCryptPasswordEncoder.matches(password,passHash);
        System.out.println(matches);
    }

    public static void path(){
        String requestUri = "/admin/permission/list/function";
        String uri = "/admin/permission/list/function";
        uri = uri.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
        System.out.println(uri);
        String regEx = "^" + uri + "$";
        System.out.println(regEx);
        boolean map = Pattern.compile(regEx).matcher(requestUri).find();
        System.out.println(map);
    }
}
