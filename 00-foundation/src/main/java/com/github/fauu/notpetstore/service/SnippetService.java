package com.github.fauu.notpetstore.service;

import com.github.fauu.notpetstore.model.entity.Snippet;

import java.util.List;

public interface SnippetService {

  List<Snippet> findAll();

  Snippet add(Snippet snippet);

}
