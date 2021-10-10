package com.matrix.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthUtil {
    public static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String passwordEncoder(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
    }
}
