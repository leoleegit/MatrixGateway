package com.matrix.core.model.rest;

import com.matrix.core.constants.ApiCode;

import java.util.List;

public class Resp<T>{
    private Integer code;
    private String msg;
    private long count;
    private T result;
    private List<T> results;

    public Resp() {
    }

    public Resp(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Resp(Integer code, T result) {
        setCode(code);
        setResult(result);
    }

    public Resp(Integer code, List<T> results) {
        setCode(code);
        setResults(results);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Resp<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getCount() {
        return count;
    }

    public Resp<T> setCount(long count) {
        this.count = count;
        return this;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
        this.count   = results!=null?results.size():0;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public static <T> Resp<T> success(T result) {
        return new Resp(ApiCode.ERROR_200.code, result);
    }

    public static Resp<String> success() {
        return new Resp(ApiCode.ERROR_200.code, ApiCode.ERROR_200.status);
    }

    public static <T> Resp<T> success(List<T> results) {
       return new Resp(ApiCode.ERROR_200.code,results);
    }

    public static <T> Resp<T> fail(String error) {
        return new Resp(ApiCode.ERROR_0.code,error);
    }

    public static <T> Resp<T> fail() {
        return new Resp(ApiCode.ERROR_0.code,ApiCode.ERROR_0.status);
    }

    public static <T> Resp<T> fail(ApiCode codeEnum) {
        return new Resp(codeEnum.code,codeEnum.status);
    }
}
