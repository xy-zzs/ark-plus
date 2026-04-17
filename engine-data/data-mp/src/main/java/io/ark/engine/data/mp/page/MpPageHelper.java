package io.ark.engine.data.mp.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.ark.engine.data.core.page.PageQuery;
import io.ark.engine.data.core.page.PageResult;
import org.springframework.util.StringUtils;

/**
 * @description: MP 分页转换工具
 *     <p>职责：PageQuery → MP Page（入参转换）；IPage → PageResult（结果转换）
 *     <p>排序字段说明：PageQuery.orderBy 传数据库列名（下划线命名），如 "created_at"
 * @author Noah Zhou
 */
public final class MpPageHelper {
  private MpPageHelper() {}

  /** PageQuery → MP Page 若 query 携带排序字段，自动追加 ORDER BY 子句 */
  public static <T> Page<T> toMpPage(PageQuery query) {
    Page<T> page = new Page<>(query.getPageNum(), query.getPageSize());

    if (StringUtils.hasText(query.getOrderBy())) {
      OrderItem orderItem =
          Boolean.TRUE.equals(query.getAsc())
              ? OrderItem.asc(query.getOrderBy())
              : OrderItem.desc(query.getOrderBy());
      page.addOrder(orderItem);
    }
    return page;
  }

  /** IPage → PageResult（解耦上层对 MP 类型的依赖） */
  public static <T> PageResult<T> toPageResult(IPage<T> mpPage) {
    return new PageResult<>(
        mpPage.getTotal(), (int) mpPage.getCurrent(), (int) mpPage.getSize(), mpPage.getRecords());
  }
}
