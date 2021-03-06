package com.matrix.admin.controller;

import com.google.gson.JsonObject;
import com.matrix.admin.service.AuthService;
import com.matrix.core.constants.ApiCode;
import com.matrix.core.constants.CS;
import com.matrix.core.controller.CommonCtrl;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.rest.req.AuthenticationReq;
import com.matrix.core.model.rest.resp.AuthenticationResp;
import com.matrix.core.cache.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
public class AuthController extends CommonCtrl {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public Resp<AuthenticationResp> login(@RequestBody AuthenticationReq req) throws UnsupportedEncodingException {
        String verCode = req.getVerCode();
        String cacheID = req.getCodeID();
        String cacheCode = RedisUtil.getObject(CS.cacheKey(CS.CacheKey.CAPTCHA,cacheID), String.class);
        if(StringUtils.isEmpty(cacheCode) || !cacheCode.equalsIgnoreCase(verCode)){
            return Resp.fail(ApiCode.ERROR_1001);
        }
        RedisUtil.del(CS.cacheKey(CS.CacheKey.CAPTCHA,cacheID));
        return authService.login(req);
    }

    @GetMapping("captcha")
    public Resp<JsonObject> captcha(){
        return authService.captcha();
    }

    @PostMapping("logout")
    public Resp<String> logout() {
        String key = getCurrentUser().getCacheKey();
        RedisUtil.del(key);
        return Resp.success();
    }
}
