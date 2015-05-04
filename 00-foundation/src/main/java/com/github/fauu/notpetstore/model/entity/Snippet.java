package com.github.fauu.notpetstore.model.entity;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

public class Snippet {

  private String id;

  private String title;

  private String content;

  private Visibility visibility;

  private LocalDateTime dateTimeAdded;

  private int numViews;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  public LocalDateTime getDateTimeAdded() {
    return dateTimeAdded;
  }

  public void setDateTimeAdded(LocalDateTime dateTimeAdded) {
    this.dateTimeAdded = dateTimeAdded;
  }

  public int getNumViews() {
    return numViews;
  }

  public void setNumViews(int numViews) {
    this.numViews = numViews;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Snippet snippet = (Snippet) o;

    return id.equals(snippet.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public enum Visibility {
    PUBLIC,
    UNLISTED;

    @Override
    public String toString() {
      switch (this) {
        case PUBLIC: return "Public";
        case UNLISTED: return "Unlisted";
        default: throw new AssertionError("Encountered unhandled value");
      }
    }
  }

}
