package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.backing.InMemoryRepository;
import com.github.fauu.notpetstore.snippet.SnippetVisit;
import com.github.fauu.notpetstore.snippet.SnippetVisitId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
class InMemorySnippetVisitRepository
    extends InMemoryRepository<SnippetVisit, SnippetVisitId>
    implements SnippetVisitRepository {

  InMemorySnippetVisitRepository() {
    items = new HashMap<>();
  }

}
