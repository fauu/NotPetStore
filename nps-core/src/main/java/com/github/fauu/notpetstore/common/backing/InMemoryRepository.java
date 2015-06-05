package com.github.fauu.notpetstore.common.backing;

import com.github.fauu.notpetstore.common.Identifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public abstract class InMemoryRepository<T extends Identifiable<ID>, ID>
    implements Repository<T, ID> {

  protected Map<ID, T> items;

  @Override
  public <S extends T> S save(S entity) {
    items.put(entity.getId(), entity);

    return entity;
  }

  @Override
  public <S extends T> List<S> save(Iterable<S> entities) {
    for (S entity : entities) {
      save(entity);
    }

    return StreamSupport.stream(entities.spliterator(), false)
                        .collect(toList());
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
  public List<T> findAll() {
    return new ArrayList<>(items.values());
  }

  @Override
  public void deleteAll() {
    items.clear();
  }

}
