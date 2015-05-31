package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.support.Page;
import com.github.fauu.notpetstore.model.support.PageRequest;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface SnippetRepository {

  boolean exists(String id);

  Optional<Snippet> findById(String id);

  List<Snippet>
  findByDeletedFalseAndDateTimeExpiresNotAfter(LocalDateTime dateTime);

  Stream<Snippet> findAll();

  Page<Snippet>
  findPageByDeletedFalseAndVisibilityPublic(PageRequest pageRequest,
                                            Snippet.SortType sortType,
                                            Optional<Snippet.SyntaxHighlighting>
                                                syntaxHighlightingFilter);

  Snippet save(Snippet snippet);

  List<Snippet> save(List<Snippet> snippets);

  void deleteAll();

}
