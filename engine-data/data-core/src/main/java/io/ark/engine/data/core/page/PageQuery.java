package io.ark.engine.data.core.page;

import lombok.Data;

/**
 * @author Noah Zhou
 * @description:
 */
@Data
public class PageQuery {
    private int pageNum = 1;
    private int pageSize = 20;
    private String orderBy;
    private Boolean asc = Boolean.TRUE;

    /** 快速构建 */
    public static PageQuery of(int pageNum, int pageSize) {
        PageQuery q = new PageQuery();
        q.setPageNum(pageNum);
        q.setPageSize(pageSize);
        return q;
    }
}
