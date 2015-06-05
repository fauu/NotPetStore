package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.Page;
import com.github.fauu.notpetstore.common.PageRequest;
import com.github.fauu.notpetstore.common.backing.InMemoryRepository;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetSort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Repository
class InMemorySnippetRepository extends InMemoryRepository<Snippet, String>
                                implements SnippetRepository {

  InMemorySnippetRepository() {
    items = new HashMap<>();
  }

  @Override
  public Set<String> findAllIds() {
    return items.keySet();
  }

  @Override
  public List<Snippet>
  findByDeletedFalseAndExpiresAtNotAfter(LocalDateTime dateTime) {
    return items.values()
                .stream()
                .filter(s -> !s.isDeleted() &&
                             s.getExpiresAt() != null &&
                             !s.getExpiresAt().isAfter(dateTime))
                .collect(toList());
  }

  @Override
  public Page<Snippet>
  findByDeletedFalseAndVisibilityPublic(PageRequest pageRequest,
                                        SnippetSort sortType,
                                        Optional<Snippet.SyntaxHighlighting>
                                            syntaxHighlightingFilter) {
    Predicate<Snippet> snippetIsNotDeletedAndPublic =
        s -> !s.isDeleted() &&
             s.getVisibility().equals(Snippet.Visibility.PUBLIC);

    Predicate<Snippet> combinedFilterPredicate =
        syntaxHighlightingFilter.isPresent() ?
            snippetIsNotDeletedAndPublic
                .and(s -> s.getSyntaxHighlighting()
                           .equals(syntaxHighlightingFilter.get())) :
            snippetIsNotDeletedAndPublic;

    List<Snippet> allItems = items.values()
                                  .stream()
                                  .filter(combinedFilterPredicate)
                                  .sorted(sortType)
                                  .collect(toList());

    int fromIndex = (pageRequest.getPageNo() - 1) * pageRequest.getPageSize();
    int toIndex = fromIndex + pageRequest.getPageSize();
    int itemCount = allItems.size();
    if (fromIndex > itemCount) {
      fromIndex = itemCount;
    }
    if (toIndex > itemCount) {
      toIndex = itemCount;
    }
    List<Snippet> pageItems = allItems.subList(fromIndex, toIndex);

    return new Page<>(pageRequest.getPageNo(),
                      pageItems,
                      pageRequest.getPageSize(),
                      allItems.size());
  }

}
