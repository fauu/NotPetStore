package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
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
  public List<Snippet> findAll() {
    try {
      final List<Snippet> snippets = snippetRepository.findAll();

      return snippets;
    } catch (DataAccessException e) {
      throw new ServiceException("Could not find snippets", e);
    }
  }

  @Override
  public Snippet add(final Snippet snippet) {
    // TODO: Verify snippet
    try {
      final Snippet savedSnippet = snippetRepository.save(snippet);

      return savedSnippet;
    } catch (DataAccessException e) {
      throw new ServiceException("Could not add snippet", e);
    }
  }

}
