package com.matrix.core.controller;

import cn.hutool.core.util.StrUtil;
import com.matrix.core.model.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractCtrl {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    protected final static int DEFAULT_PAGE_INDEX = 1;
    protected final static int DEFAULT_PAGE_SIZE  = 20;

    protected final static String DESC    = "desc";
    protected final static String ASC     = "asc";

    private final static String PAGE_INDEX   = "index";
    private final static String PAGE_SIZE    = "pageSize";
    private final static String SORT         = "sort";
    private final static String sortField    = "sortField";

    public String getParameter(String name){
        return request.getParameter(name);
    }

    public String getSort(){
        String sort = getParameter(SORT);
        return DESC.equalsIgnoreCase(sort)?DESC:ASC;
    }

    public String getSortField(){
        return getParameter(sortField);
    }

    public int getPageIndex(){
        String index = getParameter(PAGE_INDEX);
        if(index!=null && StrUtil.isNumeric(index))
            return Integer.parseInt(index);
        return DEFAULT_PAGE_INDEX;
    }

    public int getPageSize(){
        String size = getParameter(PAGE_SIZE);
        if(size!=null && StrUtil.isNumeric(size))
            return Integer.parseInt(size);
        return DEFAULT_PAGE_SIZE;
    }

    protected abstract AuthUserDetails getCurrentUser();

    public String getClientIp() {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
