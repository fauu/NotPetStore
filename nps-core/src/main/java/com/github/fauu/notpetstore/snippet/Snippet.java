package com.github.fauu.notpetstore.snippet;

import com.github.fauu.notpetstore.common.Identifiable;
import lombok.Data;
import lombok.NonNull;
import lombok.val;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public @Data class Snippet implements Identifiable<String> {

  private @NonNull String id;

  private String title;

  private @NonNull String content;

  private SyntaxHighlighting syntaxHighlighting = SyntaxHighlighting.NONE;

  private LocalDateTime dateTimeExpires;

  private String ownerPassword;

  private Visibility visibility = Visibility.UNLISTED;

  private @NonNull LocalDateTime dateTimeAdded;

  private int numViews = 0;

  private boolean deleted = false;

  public String getFilename() {
    return (title == null ? id : title.replaceAll("\\W", "_").toLowerCase())
               + ".txt";
  }

  // TODO: Decide whether Visibility and SyntaxHighlighting should be moved outside

  public enum Visibility {
    PUBLIC("Public"),
    UNLISTED("Unlisted");

    private String displayName;

    Visibility(String displayName) {
      this.displayName = displayName;
    }

    @Override
    public String toString() {
      return displayName;
    }
  }

  public enum SyntaxHighlighting {
    NONE("none", "None"),
    MARKUP("markup", "Markup (HTML)"),
    CSS("css", "CSS"),
    CLIKE("clike", "C-like"),
    JAVASCRIPT("javascript", "JavaScript"),
    JAVA("java", "Java");

    private String code;

    private String displayName;

    SyntaxHighlighting(String code, String displayName) {
      this.code = code;
      this.displayName = displayName;
    }

    public String getCode() {
      return code;
    }

    public String getDisplayName() {
      return displayName;
    }

    public static Optional<SyntaxHighlighting> fromCode(String code) {
      if (code != null) {
        for (val sh : SyntaxHighlighting.values()) {
          if (code.equalsIgnoreCase(sh.code)) {
            return Optional.of(sh);
          }
        }
      }

      return Optional.<SyntaxHighlighting>empty();
    }

    @Override
    public String toString() {
      return displayName;
    }
  }

  // TODO: Move this out of Snippet class
  public enum SortType {
    DEFAULT("default"),
    POPULAR("popular");

    private String code;

    SortType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }

    public static SortType fromCode(String code) {
      if (code != null) {
        for (val st : SortType.values()) {
          if (code.equalsIgnoreCase(st.code)) {
            return st;
          }
        }
      }

      return DEFAULT;
    }

    public Optional<Comparator<Snippet>> getComparator() {
      Comparator<Snippet> comparator = null;
      switch (this) {
        case DEFAULT:
          break;
        case POPULAR:
          comparator = (Snippet s, Snippet os) ->
                         (int) (os.getNumViews() - s.getNumViews());
          break;
        default:
          throw new IllegalStateException("Encountered unhandled value of SortType");
      }

      return Optional.ofNullable(comparator);
    }
  }

}
