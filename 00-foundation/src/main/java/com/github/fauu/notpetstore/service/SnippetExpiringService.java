package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SnippetExpiringService {

  private static final Logger LOG
      = LoggerFactory.getLogger(SnippetExpiringService.class);

  @Autowired
  private SnippetRepository snippetRepository;

  @Scheduled(fixedRate = 60 * 1000L)
  public void deleteExpiredSnippets() {
    List<Snippet> expiredSnippets =
        snippetRepository.findByDeletedFalseAndDateTimeExpiresNotAfter(LocalDateTime.now());

    expiredSnippets.forEach(s -> s.setDeleted(true));

    snippetRepository.save(expiredSnippets);

    LOG.debug("Executed deleteExpiredSnippets() in SnippetExpiringService: " +
              "Deleted " + expiredSnippets.size() + " expired snippet(s)");
  }

}
