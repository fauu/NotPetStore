package com.github.fauu.notpetstore.common.backing;

import com.github.fauu.notpetstore.common.Identifiable;

import java.util.Map;
import java.util.Optional;

public abstract class InMemoryRepository<T extends Identifiable<ID>, ID>
    implements Repository<T, ID> {

  protected Map<ID, T> items;

  @Override
  public <S extends T> S save(S entity) {
    items.put(entity.getId(), entity);

    return entity;
  }

  @Override
  public <S extends T> Iterable<S> save(Iterable<S> entities) {
    for (S entity : entities) {
      save(entity);
    }

    return entities;
  }

  @Override
  public boolean exists(ID id) {
    return items.containsKey(id);
  }

  @Override
  public Optional<T> findOne(ID id) {
    return Optional.ofNullable(items.get(id));
  }

  @Override
  public void deleteAll() {
    items.clear();
  }

}
