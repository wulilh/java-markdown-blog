package com.github.wulilinghan.jmb.common.pojo;

/**
 * @author wuliling Created By 2023-01-19 15:03
 **/
public class PageQueryBaseDto {

    private int currPage = 1; //当前页数
    private int pageSize = 5; //每页记录数

    public PageQueryBaseDto() {
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage <= 0 ? 1 : currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
