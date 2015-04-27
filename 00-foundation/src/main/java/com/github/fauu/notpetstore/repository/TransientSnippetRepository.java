package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.repository.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class TransientSnippetRepository implements SnippetRepository {

  private List<Snippet> snippetStore = new LinkedList<>();

  @Override
  public List<Snippet> findAll() {
    if (snippetStore != null) {
      return snippetStore;
    } else {
      throw new DataAccessException("Could not access snippet store");
    }
  }

  @Override
  public Snippet save(final Snippet snippet) {
    if (snippetStore.add(snippet)) {
      return snippet;
    } else {
      throw new DataAccessException("Could not add snippet to snippet store: "
                                    + snippet);
    }
  }

}
