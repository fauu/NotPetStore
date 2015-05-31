package com.github.fauu.notpetstore.repository;

import com.github.fauu.notpetstore.model.entity.Snippet;
import com.github.fauu.notpetstore.model.support.Page;
import com.github.fauu.notpetstore.model.support.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

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
  public List<Snippet> findByDeletedFalseAndDateTimeExpiresNotAfter(LocalDateTime dateTime) {
    return snippetStore.stream()
                       .filter(s -> !s.isDeleted() &&
                                    s.getDateTimeExpires() != null &&
                                    !s.getDateTimeExpires().isAfter(dateTime))
                       .collect(toList());
  }

  @Override
  public Stream<Snippet> findAll() {
    return snippetStore.stream();
  }


  @Override
  public Page<Snippet>
  findPageByDeletedFalseAndVisibilityPublic(PageRequest pageRequest,
                                            Snippet.SortType sortType,
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

    List<Snippet> allItems =
        snippetStore.stream()
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

  @Override
  public Snippet save(Snippet snippet) {
    snippetStore.removeIf(s -> s.equals(snippet));
    snippetStore.add(snippet);

    return snippet;
  }

  @Override
  public List<Snippet> save(List<Snippet> snippets) {
    snippetStore.removeAll(snippets);
    snippetStore.addAll(snippets);

    return snippets;
  }

  @Override
  public void deleteAll() {
    snippetStore.clear();
  }

}
