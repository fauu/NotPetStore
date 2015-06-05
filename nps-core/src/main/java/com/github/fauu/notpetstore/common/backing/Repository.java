package com.github.fauu.notpetstore.common.backing;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

  <S extends T> S save(S entity);

  <S extends T> List<S> save(Iterable<S> entities);

  boolean exists(ID id);

  Optional<T> findOne(ID id);

  List<T> findAll();

  void deleteAll();

}
