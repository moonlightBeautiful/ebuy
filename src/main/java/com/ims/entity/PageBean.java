package com.ims.entity;

/**
 * 分页model
 */
public class PageBean {
    /**
     * 当前页
     */
    private int page;
    /**
     * 页面尺寸
     */
    private int pageSize;
    /**
     * 起始记录数
     */
    private int start;

    public PageBean(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page-1)*pageSize;
    }

}
