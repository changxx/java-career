package com.changxx.practice.http.ms;

import java.io.Serializable;
import java.util.List;

/**
 * AppPage
 *
 * @author changxiangxiang
 * @date 2017/10/9
 */
public class AppPage implements Serializable {

    private static final long serialVersionUID = -5917984205829570274L;

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalCount;

    private Integer totalPages;

    private boolean hasNext;

    private List<AppResult> result;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<AppResult> getResult() {
        return result;
    }

    public void setResult(List<AppResult> result) {
        this.result = result;
    }
}
