package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;

import java.util.List;

public interface SnippetService {

  List<Snippet> findAll();

  Snippet add(SnippetForm snippetForm);

}
