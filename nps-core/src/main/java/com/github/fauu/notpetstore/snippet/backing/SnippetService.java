package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.BadRequestException;
import com.github.fauu.notpetstore.common.Page;
import com.github.fauu.notpetstore.common.PageRequest;
import com.github.fauu.notpetstore.common.ResourceNotFoundException;
import com.github.fauu.notpetstore.snippet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
class SnippetService {

  private @Autowired SnippetRepository snippetRepository;

  private @Autowired SnippetVisitRepository snippetVisitRepository;

  private @Autowired SnippetFormAdapter snippetFormAdapter;

  private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

  public Snippet getNonDeletedSnippetById(String id) {
    Snippet snippet =
        snippetRepository.findOne(id)
                         .orElseThrow(() -> new SnippetNotFoundException(id));

    if (snippet.isDeleted()) {
      throw new RequestedSnippetDeletedException(id);
    }

    return snippet;
  }

  public Page<Snippet> getListableSnippets(PageRequest pageRequest,
                                           Snippet.SortType sortType,
                                           Optional<Snippet.SyntaxHighlighting>
                                               syntaxHighlightingFilter) {
    if (pageRequest.getPageNo() < 1) {
      throw new BadRequestException("Incorrect page number");
    }

    Page<Snippet> snippetPage =
        snippetRepository
            .findByDeletedFalseAndVisibilityPublic(pageRequest,
                                                   sortType,
                                                   syntaxHighlightingFilter);

    if (snippetPage.getNo() > snippetPage.getNumPagesTotal()) {
      throw new ResourceNotFoundException("Non-existent page");
    }

    return snippetPage;
  }

  public Snippet addSnippet(SnippetForm snippetForm) {
    Snippet snippet =
        snippetFormAdapter.createSnippetFromSnippetForm(snippetForm);

    return snippetRepository.save(snippet);
  }

  public Snippet deleteSnippet(Snippet snippet) {
    snippet.setDeleted(true);

    return snippetRepository.save(snippet);
  }

  @Scheduled(fixedRate = 60 * 1000L)
  public void deleteExpiredSnippets() {
    List<Snippet> expiredSnippets =
        snippetRepository
            .findByDeletedFalseAndDateTimeExpiresNotAfter(LocalDateTime.now());

    expiredSnippets.forEach(s -> s.setDeleted(true));

    snippetRepository.save(expiredSnippets);

    log.debug("Executed deleteExpiredSnippets() in SnippetService: " +
              "Deleted " + expiredSnippets.size() + " expired snippet(s)");
  }

  public boolean verifySnippetOwnerPassword(Snippet snippet,
                                            String ownerPassword) {
    return bCryptPasswordEncoder.matches(
        ownerPassword, snippet.getOwnerPassword());
  }

  public Optional<String> recordSnippetVisit(Snippet snippet,
                                             Optional<String> visitorId) {
    LocalDateTime now = LocalDateTime.now();

    if (visitorId.isPresent()) {
      SnippetVisitId visitId =
          new SnippetVisitId(snippet.getId(), UUID.fromString(visitorId.get()));

       Optional<SnippetVisit> optionalLastVisit =
          snippetVisitRepository.findOne(visitId);

      if (optionalLastVisit.isPresent()) {
        SnippetVisit lastVisit = optionalLastVisit.get();

        lastVisit.setDateTime(LocalDateTime.now());

        snippetVisitRepository.save(lastVisit);
      } else {
        incrementSnippetNumViewsAndSave(snippet);

        snippetVisitRepository.save(
            new SnippetVisit(visitId.getSnippetId(),
                             visitId.getVisitorId(),
                             now));
      }

      return Optional.empty();
    } else {
      UUID newVisitorId = UUID.randomUUID();

      snippetVisitRepository.save(
          new SnippetVisit(snippet.getId(), newVisitorId, now));

      incrementSnippetNumViewsAndSave(snippet);

      return Optional.of(newVisitorId.toString());
    }
  }

  private void incrementSnippetNumViewsAndSave(Snippet snippet) {
    snippet.setNumViews(snippet.getNumViews() + 1);

    snippetRepository.save(snippet);
  }

}
