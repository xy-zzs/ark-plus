package io.ark.engine.data.mp.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.ark.engine.data.core.page.PageQuery;
import io.ark.engine.data.core.page.PageResult;
import io.ark.engine.data.core.repository.IRepository;
import io.ark.engine.data.mp.page.MpPageHelper;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 *     <p>泛型约定：
 *     <ul>
 *       <li>M — Mapper 类型，继承 BaseMapper&lt;T&gt;
 *       <li>T — 数据库实体（DO）类型
 *       <li>ID — 主键类型，通常为 Long
 *     </ul>
 *     <p>infra 层使用示例：
 *     <pre>{@code
 * @Repository
 * public class UserRepositoryImpl
 *         extends MpRepository<UserMapper, UserDO, Long>
 *         implements UserRepository {
 *     // 直接继承所有通用方法，按需 override 或添加业务查询
 * }
 * }</pre>
 *
 * @author Noah Zhou
 */
public abstract class MpRepository<M extends BaseMapper<T>, T, ID extends Serializable>
    extends ServiceImpl<M, T> implements IRepository<T, ID> {
  // ─── IRepository 标准实现 ─────────────────────────────────────────────────

  @Override
  public Optional<T> findById(ID id) {
    return Optional.ofNullable(getById(id));
  }

  @Override
  public List<T> findAll() {
    return list();
  }

  @Override
  public boolean save(T entity) {
    saveOrUpdate(entity);
    return true;
  }

  @Override
  public void deleteById(ID id) {
    removeById(id);
  }

  /** 基础分页查询（无过滤条件） 需要条件过滤时使用下方带 QueryWrapper 的重载 */
  @Override
  public PageResult<T> findPage(PageQuery query) {
    Page<T> mpPage = MpPageHelper.toMpPage(query);
    return MpPageHelper.toPageResult(page(mpPage));
  }

  // ─── MP 增强方法（IRepository 未定义，infra 层可按需调用）─────────────────

  /**
   * 带 Lambda 条件的分页查询（类型安全，推荐优先使用）
   *
   * <pre>{@code
   * findPage(query, new LambdaQueryWrapper<UserDO>()
   *     .eq(UserDO::getStatus, 1)
   *     .orderByDesc(UserDO::getCreatedAt));
   * }</pre>
   */
  public PageResult<T> findPage(PageQuery query, LambdaQueryWrapper<T> wrapper) {
    Page<T> mpPage = MpPageHelper.toMpPage(query);
    return MpPageHelper.toPageResult(page(mpPage, wrapper));
  }

  /** 带普通条件的分页查询（适合动态列名场景） */
  public PageResult<T> findPage(PageQuery query, QueryWrapper<T> wrapper) {
    Page<T> mpPage = MpPageHelper.toMpPage(query);
    return MpPageHelper.toPageResult(page(mpPage, wrapper));
  }

  /**
   * 批量保存（MP 底层走 BATCH 模式，默认每批 1000 条）
   *
   * @param entities 实体列表
   * @param batchSize 每批数量，建议 500~1000
   */
  public boolean batchSave(List<T> entities, int batchSize) {
    return saveBatch(entities, batchSize);
  }

  /** 按条件查询单条（查到多条时抛异常，MP 的 getOne 行为） */
  public Optional<T> findOne(LambdaQueryWrapper<T> wrapper) {
    return Optional.ofNullable(getOne(wrapper));
  }

  /** 按条件查询列表 */
  public List<T> findList(LambdaQueryWrapper<T> wrapper) {
    return list(wrapper);
  }

  /** 按条件统计数量 */
  public long count(LambdaQueryWrapper<T> wrapper) {
    return count(wrapper);
  }
}
