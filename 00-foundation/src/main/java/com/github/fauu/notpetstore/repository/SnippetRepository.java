package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.support.Page;
import com.github.fauu.notpetstore.model.support.PageRequest;

import java.util.Optional;
import java.util.stream.Stream;

public interface SnippetRepository {

  boolean exists(String id);

  Optional<Snippet> findById(String id);

  Stream<Snippet> findAll();

  Page<Snippet> findPageOfSortedByDeletedFalseAndVisibilityPublic(PageRequest pageRequest, Snippet.SortType sortType);

  Snippet save(Snippet snippet);

  void deleteAll();

}
