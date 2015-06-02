package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.Page;
import com.github.fauu.notpetstore.common.PageRequest;
import com.github.fauu.notpetstore.common.backing.Repository;
import com.github.fauu.notpetstore.snippet.Snippet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

interface SnippetRepository extends Repository<Snippet, String> {

  Set<String> findAllIds();

  List<Snippet>
  findByDeletedFalseAndDateTimeExpiresNotAfter(LocalDateTime dateTime);

  Page<Snippet>
  findByDeletedFalseAndVisibilityPublic(PageRequest pageRequest,
                                        Snippet.SortType sortType,
                                        Optional<Snippet.SyntaxHighlighting>
                                            syntaxHighlightingFilter);

}
