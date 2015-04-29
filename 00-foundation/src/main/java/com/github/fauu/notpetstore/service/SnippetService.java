package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.repository.exception.DataAccessException;
import com.github.fauu.notpetstore.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnippetService {

  @Autowired
  private SnippetRepository snippetRepository;

  public List<Snippet> findAll() throws ServiceException {
    try {
      List<Snippet> snippets = snippetRepository.findAll();

      return snippets;
    } catch (DataAccessException e) {
      throw new ServiceException("Could not find snippets", e);
    }
  }

  public Snippet add(SnippetForm snippetForm) {
    // TODO: Verify snippet

    Snippet snippet = new Snippet();
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());

    try {
      return snippetRepository.save(snippet);
    } catch (DataAccessException e) {
      throw new ServiceException("Could not add snippet", e);
    }
  }

}
