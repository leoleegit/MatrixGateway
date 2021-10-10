package com.matrix.admin.test;

import cn.hutool.core.lang.UUID;
import com.matrix.core.jwt.JWTPayload;
import com.matrix.core.jwt.JWTUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtilsTester {
    private static String secret = "fdsfsdfasdafsdfsdfasdfds3231232132134123";
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(UUID.fastUUID());
        JWTPayload jwtPayload = new JWTPayload();
        jwtPayload.setPayloadID("1122");
        jwtPayload.setCreatedAt(new Date());
        jwtPayload.setExpiresIn(new Date(System.currentTimeMillis()+3600*1000l));
        jwtPayload.setUserid("leo");

        String token = JWTUtils.generateToken(jwtPayload,secret);
        System.out.println(token);

        System.out.println(JWTUtils.parseToken(token,secret));
    }
}
