package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;

import java.util.List;

public interface SnippetRepository {

  List<Snippet> findAll();

  Snippet save(Snippet snippet);

  void deleteAll();

}
