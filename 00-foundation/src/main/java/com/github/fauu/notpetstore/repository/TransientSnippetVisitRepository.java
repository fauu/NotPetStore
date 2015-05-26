package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.SnippetVisit;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransientSnippetVisitRepository implements SnippetVisitRepository {

  private List<SnippetVisit> snippetVisitStore;

  public TransientSnippetVisitRepository() {
    snippetVisitStore = new LinkedList<>();
  }

  @Override
  public Optional<SnippetVisit> findBySnippetIdAndVisitorId(String snippetId,
                                                            UUID visitorId) {
    return snippetVisitStore.stream()
        .filter(sv -> sv.getSnippetId().equals(snippetId) &&
                      sv.getVisitorId().equals(visitorId))
        .findFirst();
  }

  @Override
  public SnippetVisit save(SnippetVisit snippetVisit) {
    snippetVisitStore.removeIf(sv -> sv.equals(snippetVisit));
    snippetVisitStore.add(snippetVisit);

    return snippetVisit;
  }

}
