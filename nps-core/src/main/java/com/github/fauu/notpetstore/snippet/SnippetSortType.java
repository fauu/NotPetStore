package com.github.fauu.notpetstore.snippet;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum SnippetSortType {

  DEFAULT("default"),
  POPULAR("popular");

  private final @NonNull String code;

  public static SnippetSortType fromCode(String code) {
    if (code != null) {
      for (SnippetSortType st : SnippetSortType.values()) {
        if (code.equalsIgnoreCase(st.code)) {
          return st;
        }
      }
    }

    return DEFAULT;
  }

  // TODO: Look into the possibility of implementing multiple comparators for Snippet class
  public Optional<Comparator<Snippet>> getComparator() {
    Comparator<Snippet> comparator = null;

    switch (this) {
      case DEFAULT:
        break;
      case POPULAR:
        comparator =
            (Snippet s, Snippet o) -> (int) (o.getNumViews() - s.getNumViews());
        break;
      default:
        throw new IllegalStateException("Encountered unhandled value of " +
                                        "SnippetSortType");
    }

    return Optional.ofNullable(comparator);
  }

}

