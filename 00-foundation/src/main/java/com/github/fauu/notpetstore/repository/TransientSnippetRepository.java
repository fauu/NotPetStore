package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.support.Page;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class TransientSnippetRepository implements SnippetRepository {

  private List<Snippet> snippetStore;

  public TransientSnippetRepository() {
    snippetStore = new LinkedList<>();
  }

  @Override
  public boolean exists(String id) {
    return snippetStore.stream()
                       .anyMatch(s -> s.getId().equals(id));
  }

  @Override
  public Optional<Snippet> findById(String id) {
    return snippetStore.stream()
                       .filter(s -> s.getId().equals(id))
                       .findFirst();
  }

  @Override
  public Stream<Snippet> findAll() {
    return snippetStore.stream();
  }


  @Override
  public Page<Snippet> findPageByDeletedFalseAndVisibilityPublic(int pageNo, int pageSize) {
    List<Snippet> items =
        snippetStore.stream()
            .filter(s -> !s.isDeleted())
            .filter(s -> s.getVisibility().equals(Snippet.Visibility.PUBLIC))
            .skip((pageNo - 1) * pageSize)
            .limit(pageSize)
            .collect(Collectors.toList());

    return new Page<>(pageNo, items, pageSize, snippetStore.size());
  }

  @Override
  public Snippet save(Snippet snippet) {
    snippetStore.removeIf(s -> s.equals(snippet));
    snippetStore.add(snippet);

    return snippet;
  }

  @Override
  public void deleteAll() {
    snippetStore.clear();
  }

}
