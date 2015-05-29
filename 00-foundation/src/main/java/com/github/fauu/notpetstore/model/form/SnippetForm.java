package com.github.fauu.notpetstore.model.form;

import com.github.fauu.notpetstore.model.entity.Snippet;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

public class SnippetForm {

  @Size(max = 80, message = "{snippetForm.error.titleTooLong}")
  private String title;

  @NotEmpty(message = "{snippetForm.error.contentEmpty}")
  @Size(min = 140, max = 5000, message = "{snippetForm.error.contentIncorrectLength}")
  private String content;

  private Snippet.SyntaxHighlighting syntaxHighlighting;

  private SnippetForm.ExpirationMoment expirationMoment;

  @Size(min = 5, max = 30, message = "{snippetForm.error.ownerPasswordIncorrectLength}")
  private String ownerPassword;

  private Snippet.Visibility visibility;

  public SnippetForm() {
    visibility = Snippet.Visibility.PUBLIC;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Snippet.SyntaxHighlighting getSyntaxHighlighting() {
    return syntaxHighlighting;
  }

  public void setSyntaxHighlighting(Snippet.SyntaxHighlighting syntaxHighlighting) {
    this.syntaxHighlighting = syntaxHighlighting;
  }

  public ExpirationMoment getExpirationMoment() {
    return expirationMoment;
  }

  public void setExpirationMoment(ExpirationMoment expirationMoment) {
    this.expirationMoment = expirationMoment;
  }

  public String getOwnerPassword() {
    return ownerPassword;
  }

  public void setOwnerPassword(String ownerPassword) {
    this.ownerPassword = ownerPassword;
  }

  public Snippet.Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Snippet.Visibility visibility) {
    this.visibility = visibility;
  }

  public Snippet.SyntaxHighlighting[] getSyntaxHighlightingValues() {
    return Snippet.SyntaxHighlighting.values();
  }

  public ExpirationMoment[] getExpirationMomentValues() {
    return ExpirationMoment.values();
  }

  public Snippet.Visibility[] getVisibilityValues() {
    return Snippet.Visibility.values();
  }

  @Override
  public String toString() {
    return "SnippetForm{" +
        "title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SnippetForm that = (SnippetForm) o;

    if (title != null ? !title.equals(that.title) : that.title != null)
      return false;
    return !(content != null ? !content.equals(that.content) : that.content != null);

  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (content != null ? content.hashCode() : 0);
    return result;
  }

  public enum ExpirationMoment {
    INDEFINITE(Optional.<Duration>empty(), "Never"),
    TEN_MINUTES(Optional.of(Duration.ofMinutes(10L)),  "In 10 minutes"),
    ONE_HOUR(Optional.of(Duration.ofHours(1L)), "In an hour"),
    ONE_DAY(Optional.of(Duration.ofDays(1)), "In a day"),
    ONE_WEEK(Optional.of(Duration.ofDays(7)), "In a week"),
    ONE_MONTH(Optional.of(Duration.ofDays(30)), "In a month");

    private Optional<Duration> timeUntil;

    private String displayName;

    ExpirationMoment(Optional<Duration> timeUntil, String displayName) {
      this.timeUntil = timeUntil;
      this.displayName = displayName;
    }

    public Optional<Duration> getTimeUntil() {
      return timeUntil;
    }

    public String getDisplayName() {
      return displayName;
    }
  }


}
