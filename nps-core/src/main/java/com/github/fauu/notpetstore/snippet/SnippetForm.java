package com.github.fauu.notpetstore.snippet;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Optional;

@NoArgsConstructor
public @Data class SnippetForm {

  @Size(max = 80, message = "{snippetForm.error.titleTooLong}")
  private String title;

  @NotEmpty(message = "{snippetForm.error.contentEmpty}")
  @Size(min = 140,
        max = 5000,
        message = "{snippetForm.error.contentIncorrectLength}")
  private @NonNull String content;

  private @NonNull Snippet.SyntaxHighlighting syntaxHighlighting;

  private @NonNull SnippetForm.ExpirationMoment expirationMoment;

  @Size(min = 5,
        max = 30,
        message = "{snippetForm.error.ownerPasswordIncorrectLength}")
  private String ownerPassword;

  private @NonNull Snippet.Visibility visibility;

  {
    syntaxHighlighting = Snippet.SyntaxHighlighting.NONE;
    expirationMoment = ExpirationMoment.NEVER;
  }

  public SnippetForm(Snippet snippet, boolean hideSensitiveData) {
    title = snippet.getTitle();
    content = snippet.getContent();
    syntaxHighlighting = snippet.getSyntaxHighlighting();
    visibility = snippet.getVisibility();

    if (!hideSensitiveData) {
      ownerPassword = snippet.getOwnerPassword();
    }
  }

  // TODO: Transform both into properties with @Setter(AccessLevel.NONE)?

  public ExpirationMoment[] getExpirationMomentValues() {
    return ExpirationMoment.values();
  }

  public Snippet.Visibility[] getVisibilityValues() {
    return Snippet.Visibility.values();
  }

  @RequiredArgsConstructor
  @Getter
  public enum ExpirationMoment {
    NEVER(Optional.<Duration>empty(), "Never"),
    IN_TEN_MINUTES(Optional.of(Duration.ofMinutes(10L)),  "In 10 minutes"),
    IN_ONE_HOUR(Optional.of(Duration.ofHours(1L)), "In an hour"),
    IN_ONE_DAY(Optional.of(Duration.ofDays(1)), "In a day"),
    IN_ONE_WEEK(Optional.of(Duration.ofDays(7)), "In a week"),
    IN_ONE_MONTH(Optional.of(Duration.ofDays(30)), "In a month");

    private final Optional<Duration> timeUntil;

    private final String displayName;

    @Override
    public String toString() {
      return displayName;
    }
  }

}
