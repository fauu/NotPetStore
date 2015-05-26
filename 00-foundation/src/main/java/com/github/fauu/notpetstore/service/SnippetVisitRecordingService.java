package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.entity.SnippetVisit;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.repository.SnippetVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SnippetVisitRecordingService {

  @Autowired
  private SnippetRepository snippetRepository;

  @Autowired
  private SnippetVisitRepository snippetVisitRepository;

  public Optional<String> recordSnippetVisit(Snippet snippet,
                                             Optional<String> visitorId) {
    if (visitorId.isPresent()) {
      Optional<SnippetVisit> optionalLastVisit =
          snippetVisitRepository
              .findBySnippetIdAndVisitorId(snippet.getId(),
                                           UUID.fromString(visitorId.get()));

      if (optionalLastVisit.isPresent()) {
        SnippetVisit lastVisit = optionalLastVisit.get();

        lastVisit.setDateTime(LocalDateTime.now());

        snippetVisitRepository.save(lastVisit);
      } else {
        incrementSnippetNumViewsAndSave(snippet);

        createAndSaveSnippetVisit(snippet, UUID.fromString(visitorId.get()));
      }

      return Optional.empty();
    } else {
      UUID newVisitorId = UUID.randomUUID();

      createAndSaveSnippetVisit(snippet, newVisitorId);

      incrementSnippetNumViewsAndSave(snippet);

      return Optional.of(newVisitorId.toString());
    }
  }

  private void incrementSnippetNumViewsAndSave(Snippet snippet) {
    snippet.setNumViews(snippet.getNumViews() + 1);

    snippetRepository.save(snippet);
  }

  private void createAndSaveSnippetVisit(Snippet snippet, UUID visitorId) {
    SnippetVisit snippetVisit = new SnippetVisit();

    snippetVisit.setSnippetId(snippet.getId());
    snippetVisit.setVisitorId(visitorId);
    snippetVisit.setDateTime(LocalDateTime.now());

    snippetVisitRepository.save(snippetVisit);
  }

}
