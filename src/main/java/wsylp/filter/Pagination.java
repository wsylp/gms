package wsylp.filter;

/**
 *  分页条件
 * Created by wsylp on 2017/9/7.
 */
public class Pagination {
    private int limit;
    private int page;
    private int start;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
