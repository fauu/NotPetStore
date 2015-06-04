package com.github.fauu.notpetstore.snippet.backing;

import com.github.fauu.notpetstore.common.Page;
import com.github.fauu.notpetstore.common.PageRequest;
import com.github.fauu.notpetstore.common.backing.InMemoryRepository;
import com.github.fauu.notpetstore.snippet.Snippet;
import com.github.fauu.notpetstore.snippet.SnippetSortType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
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
  findByDeletedFalseAndDateTimeExpiresNotAfter(LocalDateTime dateTime) {
    return items.values()
                .stream()
                .filter(s -> !s.isDeleted() &&
                             s.getDateTimeExpires() != null &&
                             !s.getDateTimeExpires().isAfter(dateTime))
                .collect(toList());
  }

  @Override
  public Page<Snippet>
  findByDeletedFalseAndVisibilityPublic(PageRequest pageRequest,
                                        SnippetSortType sortType,
                                        Optional<Snippet.SyntaxHighlighting>
                                            syntaxHighlightingFilter) {
    Comparator<Snippet> dateTimeAddedPresortComparator =
        Comparator.comparing(Snippet::getDateTimeAdded).reversed();

    Comparator<Snippet> combinedSortComparator =
        sortType.getComparator().isPresent() ?
            sortType.getComparator()
                    .get()
                    .thenComparing(dateTimeAddedPresortComparator) :
            dateTimeAddedPresortComparator;

    Predicate<Snippet> nonDeletedAndPublicPredicate =
        s -> !s.isDeleted() &&
             s.getVisibility().equals(Snippet.Visibility.PUBLIC);

    Predicate<Snippet> combinedFilterPredicate =
        syntaxHighlightingFilter.isPresent() ?
            nonDeletedAndPublicPredicate
                .and(s -> s.getSyntaxHighlighting()
                           .equals(syntaxHighlightingFilter.get())) :
            nonDeletedAndPublicPredicate;

    List<Snippet> allItems = items.values()
                                  .stream()
                                  .filter(combinedFilterPredicate)
                                  .sorted(combinedSortComparator)
                                  .collect(toList());

    int fromIndex = (pageRequest.getPageNo() - 1) * pageRequest.getPageSize();
    int toIndex = fromIndex + pageRequest.getPageSize();
    if (toIndex > allItems.size()) {
      toIndex = allItems.size();
    }
    List<Snippet> pageItems = allItems.subList(fromIndex, toIndex);

    return new Page<>(pageRequest.getPageNo(),
                      pageItems,
                      pageRequest.getPageSize(),
                      allItems.size());
  }

}
