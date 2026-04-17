package io.ark.engine.data.core.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Noah Zhou
 * @description:
 */
public interface IConverter<T, S> {
  T toTarget(S source);

  S toSource(T target);

  default List<T> toTargetList(List<S> sources) {
    return sources.stream().map(this::toTarget).collect(Collectors.toList());
  }
}
