package com.matrix.core.model.rest.req;

public class CommonQueryReq {
    protected final static int DEFAULT_PAGE_INDEX = 1;
    protected final static int DEFAULT_PAGE_SIZE  = 20;

    public final static String DESC    = "desc";
    public final static String ASC     = "asc";

    private int current;
    private int pageSize;
    private String sort;
    private String sortField;

    public int getCurrent() {
        return current==0?DEFAULT_PAGE_INDEX:current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize==0?DEFAULT_PAGE_SIZE:pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return DESC.equalsIgnoreCase(sort)?DESC:ASC;
    }

    public boolean isAsc(){
        return ASC.equals(getSort());
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}
