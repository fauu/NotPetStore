package com.github.fauu.notpetstore.model.entity;

import java.time.LocalDateTime;

public class Snippet {

  private String id;

  private String title;

  private String content;

  private SyntaxHighlighting syntaxHighlighting;

  private String ownerPassword;

  private Visibility visibility;

  private LocalDateTime dateTimeAdded;

  private int numViews;

  private boolean deleted;

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

  public SyntaxHighlighting getSyntaxHighlighting() {
    return syntaxHighlighting;
  }

  public void setSyntaxHighlighting(SyntaxHighlighting syntaxHighlighting) {
    this.syntaxHighlighting = syntaxHighlighting;
  }

  public String getOwnerPassword() {
    return ownerPassword;
  }

  public void setOwnerPassword(String ownerPassword) {
    this.ownerPassword = ownerPassword;
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

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
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

    @Override
    public String toString() {
      return displayName;
    }
  }

}
