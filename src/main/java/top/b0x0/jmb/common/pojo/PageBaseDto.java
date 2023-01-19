package top.b0x0.jmb.common.pojo;

/**
 * @author wuliling Created By 2023-01-19 15:03
 **/

public class PageBaseDto {

    private int currPage = 1; //当前页数
    private int pageSize = 10; //每页记录数

    public PageBaseDto() {
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
}
