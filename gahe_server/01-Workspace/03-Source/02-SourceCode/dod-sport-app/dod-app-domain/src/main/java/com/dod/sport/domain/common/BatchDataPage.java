package com.dod.sport.domain.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * Created by defi on 2017-08-10.
 * 前端获取批量数据
 * 根据所传页码获取数据
 */
public class BatchDataPage<T> implements Serializable {

    private static final long serialVersionUID = -6718318055602213075L;

    /** 当前页 */
    private int page=1;
    /** 分页数 */
    private int pagesize=10;
    /** 具体数据 */
    private List<T> rows;
    /** 开始行*/
    private int offset;

    /**总记录数*/
    private long totalRecords;

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getOffset() {
        if(page == 0){
            page  = 1;
        }
        offset = (page - 1) * pagesize;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
