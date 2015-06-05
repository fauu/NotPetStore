package com.github.fauu.notpetstore.snippet;

import com.github.fauu.notpetstore.common.Identifiable;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

public @Data class Snippet implements Identifiable<String> {

  private @NonNull String id;

  private String title;

  private @NonNull String content;

  private SyntaxHighlighting syntaxHighlighting;

  private LocalDateTime expiresAt;

  private String ownerPassword;

  private Visibility visibility;

  private @NonNull LocalDateTime createdAt;

  private int viewCount;

  private boolean deleted;

  {
    syntaxHighlighting = SyntaxHighlighting.NONE;
    visibility = Visibility.UNLISTED;
    viewCount = 0;
    deleted = false;
  }

  public String getFilename() {
    return (title == null ? id : title.replaceAll("\\W", "_").toLowerCase());
  }

  @RequiredArgsConstructor
  @Getter
  public enum Visibility {
    PUBLIC("Public"),
    UNLISTED("Unlisted");

    private final @NonNull String displayName;

    @Override
    public String toString() {
      return displayName;
    }
  }

  @RequiredArgsConstructor
  @Getter
  public enum SyntaxHighlighting {
    NONE("none", "None"),
    MARKUP("markup", "Markup (HTML)"),
    CSS("css", "CSS"),
    CLIKE("clike", "C-like"),
    JAVASCRIPT("javascript", "JavaScript"),
    JAVA("java", "Java");

    private final @NonNull String code;

    private final @NonNull String displayName;

    public static Optional<SyntaxHighlighting> ofCode(String code) {
      if (code != null) {
        for (SyntaxHighlighting sh : SyntaxHighlighting.values()) {
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

}
