package com.github.fauu.notpetstore.snippet;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum SnippetSort implements Comparator<Snippet> {

  RECENT_FIRST("recent") {
    @Override
    public int compare(Snippet s, Snippet os) {
      return os.getCreatedAt().compareTo(s.getCreatedAt());
    }
  },
  POPULAR_FIRST("popular") {
    private final Comparator<Snippet> reversedViewCountComparator =
        (s, os) -> os.getViewCount() - s.getViewCount();

    @Override
    public int compare(Snippet s, Snippet os) {
      return reversedViewCountComparator.thenComparing(RECENT_FIRST)
                                        .compare(s, os);
    }
  };

  private final @NonNull String code;

  public static Optional<SnippetSort> ofCode(String code) {
    if (code != null) {
      for (SnippetSort ss : SnippetSort.values()) {
        if (code.equalsIgnoreCase(ss.code)) {
          return Optional.of(ss);
        }
      }
    }

    return Optional.<SnippetSort>empty();
  }

}
