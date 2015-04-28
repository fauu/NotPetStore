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
public class SnippetServiceImpl implements SnippetService {

  @Autowired
  private SnippetRepository snippetRepository;

  @Override
  public List<Snippet> findAll() throws ServiceException {
    try {
      final List<Snippet> snippets = snippetRepository.findAll();

      return snippets;
    } catch (DataAccessException e) {
      throw new ServiceException("Could not find snippets", e);
    }
  }

  @Override
  public Snippet add(final SnippetForm snippetForm) {
    // TODO: Verify snippet

    final Snippet snippet = new Snippet();
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());

    try {
      return snippetRepository.save(snippet);
    } catch (DataAccessException e) {
      throw new ServiceException("Could not add snippet", e);
    }
  }

}
