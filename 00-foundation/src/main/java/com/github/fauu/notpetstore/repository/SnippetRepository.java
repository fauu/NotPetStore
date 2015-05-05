package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;

import java.util.stream.Stream;

public interface SnippetRepository {

  boolean exists(String id);

  Stream<Snippet> findAll();

  Stream<Snippet> findByDeletedFalseAndVisibilityPublic();

  Snippet save(Snippet snippet);

  void deleteAll();

}
