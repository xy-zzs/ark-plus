package io.ark.engine.data.core.page;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Noah Zhou
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

  private long total;
  private int pageNum;
  private int pageSize;
  private List<T> records;

  public static <T> PageResult<T> empty(PageQuery query) {
    return new PageResult<>(0L, query.getPageNum(), query.getPageSize(), Collections.emptyList());
  }
}
