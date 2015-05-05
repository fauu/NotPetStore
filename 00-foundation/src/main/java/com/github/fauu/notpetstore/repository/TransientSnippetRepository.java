package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.form.SnippetForm;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class TransientSnippetRepository implements SnippetRepository {

  private List<Snippet> snippetStore;

  public TransientSnippetRepository() {
    snippetStore = new LinkedList<>();
  }

  @Override
  public boolean exists(String id) {
    return snippetStore.stream().anyMatch(s -> s.getId().equals(id));
  }

  @Override
  public List<Snippet> findAll() {
    return snippetStore;
  }

  @Override
  public List<Snippet> findAllPublicSnippets() {
    return snippetStore.stream()
                       .filter(s -> s.getVisibility() ==
                                    Snippet.Visibility.PUBLIC)
                       .collect(toList());
  }

  @Override
  public Snippet save(Snippet snippet) {
    snippetStore.add(snippet);

    return snippet;
  }

  @Override
  public void deleteAll() {
    snippetStore.clear();
  }

}
