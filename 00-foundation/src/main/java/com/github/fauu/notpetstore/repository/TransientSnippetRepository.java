package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class TransientSnippetRepository implements SnippetRepository {

  private List<Snippet> snippetStore = new LinkedList<>();

  @Override
  public List<Snippet> findAll() {
    return snippetStore;
  }

  @Override
  public Snippet save(final Snippet snippet) {
    snippetStore.add(snippet);

    return snippet;
  }

}
