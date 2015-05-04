package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import com.github.fauu.notpetstore.repository.SnippetRepository;
import com.github.fauu.notpetstore.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastingService {

  @Autowired
  private SnippetRepository snippetRepository;

  public List<Snippet> getAllSnippets() throws ServiceException {
    return snippetRepository.findAll();
  }

  public Snippet addSnippet(SnippetForm snippetForm) {
    Snippet snippet = new Snippet();
    snippet.setTitle(snippetForm.getTitle());
    snippet.setContent(snippetForm.getContent());

    return snippetRepository.save(snippet);
  }

}
