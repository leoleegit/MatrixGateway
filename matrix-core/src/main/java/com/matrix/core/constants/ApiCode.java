package com.matrix.core.constants;

public enum ApiCode {
    ERROR_200(200, "OK"),
    ERROR_204(204,"No Content"),

    ERROR_400(400,"Bad Request"),
    ERROR_401(401,"Unauthorized"),
    ERROR_403(403,"Forbidden"),
    ERROR_404(404,"Not Found"),
    ERROR_405(405,"Method Not Allowed"),

    //The request could not be processed because it conflicts with some established rule of the system. For example, a person may not be added to a room more than once
    ERROR_409(409,"Conflict"),

    //The requested resource is no longer available
    ERROR_410(410,"Gone"),

    ERROR_415(415,"Unsupported Media Type"),
    ERROR_423(423,"Locked"),
    ERROR_428(428,"Precondition Required"),
    ERROR_429(429,"Too Many Requests"),

    ERROR_500(500,"Internal Server Error"),
    ERROR_502(502,"Bad Gateway"),
    ERROR_503(503,"Service Unavailable"),
    ERROR_504(504,"Gateway Timeout"),


    //Login
    ERROR_1001(1001, "The captcha is incorrect"),
    ERROR_1002(1002, "Incorrect username/password"),

    ERROR_0(0,"System error");

    public int code;
    public String status;

    ApiCode(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
