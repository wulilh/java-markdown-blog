package top.b0x0.jmb.common.pojo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuliling Created By 2023-01-19 14:45
 **/
public class PageResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 3602764259182858275L;

    private int total; //总记录数
    private int currPage = 1; //当前页数
    private int pageSize = 10; //每页记录数
    private int totalPage; //总页数
    private List<?> list; //列表数据

    private int first; //第一页
    private int last; //下一页

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
        this.list = list;
        this.total = list.size();
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) total / pageSize);
        long skipNum = (long) pageSize * (currPage - 1);
        skipNum = skipNum <= 0 ? 0 : skipNum;
        this.list = list.stream().skip(skipNum).limit(pageSize).collect(Collectors.toList());
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

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
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
