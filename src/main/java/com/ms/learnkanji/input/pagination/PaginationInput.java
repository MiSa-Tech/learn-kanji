package com.ms.learnkanji.input.pagination;

import com.ms.learnkanji.commons.Ordering;

public class PaginationInput {
    private Integer pageNum;
    private Integer pageSize;
    private Ordering ordering;

    public PaginationInput() {
    }

    public PaginationInput(Integer pageNum,
                           Integer pageSize,
                           Ordering ordering) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.ordering = ordering;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Ordering getOrdering() {
        return ordering;
    }

    public void setOrdering(Ordering ordering) {
        this.ordering = ordering;
    }
}
