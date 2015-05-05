package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;

import java.util.List;

public interface SnippetRepository {

  boolean exists(String id);

  List<Snippet> findAll();

  List<Snippet> findAllPublicSnippets();

  Snippet save(Snippet snippet);

  void deleteAll();

}
