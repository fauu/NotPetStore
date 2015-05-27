package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.support.Page;

import java.util.Optional;
import java.util.stream.Stream;

public interface SnippetRepository {

  boolean exists(String id);

  Optional<Snippet> findById(String id);

  Stream<Snippet> findAll();

  Page<Snippet> findPageByDeletedFalseAndVisibilityPublic(int pageNo, int pageSize);

  Snippet save(Snippet snippet);

  void deleteAll();

}
