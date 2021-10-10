package com.matrix.core.jwt;

import com.matrix.core.model.rest.MyGsonHttpMessageConverter;
import lombok.Data;

import java.util.Date;

@Data
public class JWTPayload {
    private String payloadID;
    private String userid;
    private Date createdAt;
    private Date expiresIn;

    public JWTPayload(String payloadID, String userid, Date createdAt, Date expiresIn) {
        this.payloadID = payloadID;
        this.userid = userid;
        this.createdAt = createdAt;
        this.expiresIn = expiresIn;
    }

    public JWTPayload() {
    }

    public String toString(){
        return MyGsonHttpMessageConverter.myGson().toJson(this);
    }

    public static JWTPayload parse(String string){
        return MyGsonHttpMessageConverter.myGson().fromJson(string,JWTPayload.class);
    }
}
