package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnippetServiceImpl implements SnippetService {

  @Autowired
  private SnippetRepository snippetRepository;

  @Override
  public List<Snippet> findAll() {
    return snippetRepository.findAll();
  }

  @Override
  public Snippet add(final Snippet snippet) {
    // TODO: Verify snippet
    return snippetRepository.save(snippet);
  }

}
