package com.github.wulilinghan.jmb.common.pojo;

import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuliling Created By 2023-01-19 14:45
 **/
@Slf4j
public class PageResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 3602764259182858275L;

    private int total; //总记录数
    private int currPage = 1; //当前页数
    private int pageSize = 10; //每页记录数
    private int totalPage; //总页数
    private List<?> list; //列表数据

    private boolean first; //是否是第一页
    private boolean last; //是否是最后一页

    public PageResult() {
    }

    /**
     * 分页
     *
     * @param list     列表数据
     * @param pageSize 每页记录数
     * @param currPage 当前页数
     */
    public PageResult(List<?> list, int currPage, int pageSize) {
        if (list == null) {
            log.warn("paramList is null");
            list = new ArrayList<>();
        }
        this.list = list;
        this.total = list.size();
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) total / pageSize);
        long skipNum = (long) (pageSize <= 0 ? 5 : pageSize) * (currPage <= 0 ? 1 : currPage - 1);
        this.list = list.stream().skip(skipNum).limit(pageSize).collect(Collectors.toList());

        this.first = currPage == 1;
        this.last = this.totalPage == currPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", first=" + first +
                ", last=" + last +
                '}';
    }
}
