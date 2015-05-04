package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.service.exception.ServiceException;
import com.github.fauu.notpetstore.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PastingService {

  // TODO: Externalize this?
  private static final int NEW_SNIPPET_ID_LENGTH = 8;

  @Autowired
  private SnippetRepository snippetRepository;

  public List<Snippet> getAllSnippets() throws ServiceException {
    return snippetRepository.findAll();
  }

  public Snippet addSnippet(SnippetForm snippetForm) {
    Snippet snippet = new Snippet();
    snippet.setId(generateUniqueId(NEW_SNIPPET_ID_LENGTH));
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());
    snippet.setVisibility(snippetForm.getVisibility());
    snippet.setDateTimeAdded(LocalDateTime.now());
    snippet.setNumViews(0);

    return snippetRepository.save(snippet);
  }

  private String generateUniqueId(int length) {
    String id;
    do {
      id = IdGenerator.generate(length);
    } while (snippetRepository.exists(id));

    return id;
  }

}
