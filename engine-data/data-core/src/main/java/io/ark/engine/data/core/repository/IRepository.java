package io.ark.engine.data.core.repository;

import io.ark.engine.data.core.page.PageQuery;
import io.ark.engine.data.core.page.PageResult;
import java.util.List;
import java.util.Optional;

/**
 * @author Noah Zhou
 * @description:
 */
public interface IRepository<T, ID> {

  Optional<T> findById(ID id);

  List<T> findAll();

  boolean save(T entity);

  void deleteById(ID id);

  PageResult<T> findPage(PageQuery query);
}
