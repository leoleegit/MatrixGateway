package com.matrix.core.jwt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class JWTUtils {
    private static final String CLAIM_NAME = "payload";
    private static final String ISSUER     = "matrix";

    public static String generateToken(JWTPayload jwtPayload, String secret) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        byte[] dataBytes = SecureUtil.des(secret.getBytes(StandardCharsets.UTF_8)).encrypt(jwtPayload.toString());
        String payload   = Base64.encode(dataBytes);
        JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withClaim(CLAIM_NAME, payload).withJWTId(jwtPayload.getPayloadID());
        if (jwtPayload.getExpiresIn() != null) {
            builder.withExpiresAt(jwtPayload.getExpiresIn());
        }
        return builder.sign(algorithm);
    }

    public static JWTPayload parseToken(String token, String secret) throws UnsupportedEncodingException {
        Algorithm algorithm   = Algorithm.HMAC256(secret);
        JWTVerifier verifier  = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String payload        = decodedJWT.getClaim(CLAIM_NAME).asString();
        byte[] dataBytes      = Base64.decode(payload);
        byte[] decryptBytes   = SecureUtil.des(secret.getBytes(StandardCharsets.UTF_8)).decrypt(dataBytes);
        return JWTPayload.parse(new String(decryptBytes,StandardCharsets.UTF_8));
    }
}
