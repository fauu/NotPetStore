package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.SnippetVisit;

import java.util.Optional;
import java.util.UUID;

public interface SnippetVisitRepository {

  Optional<SnippetVisit> findBySnippetIdAndVisitorId(String snippetId,
                                                     UUID visitorId);

  SnippetVisit save(SnippetVisit snippetVisit);

}
