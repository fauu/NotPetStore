package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.exception.DataAccessException;

import java.util.List;

public interface SnippetRepository {

  List<Snippet> findAll() throws DataAccessException;

  Snippet save(Snippet snippet) throws DataAccessException;

}
