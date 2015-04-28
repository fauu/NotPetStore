package com.github.fauu.notpetstore.model.entity;

public class Snippet {

  private String title;

  private String content;

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

  @Override
  public String toString() {
    return "Snippet{" +
        "title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Snippet snippet = (Snippet) o;

    if (title != null ? !title.equals(snippet.title) : snippet.title != null)
      return false;
    return content.equals(snippet.content);

  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + content.hashCode();
    return result;
  }

}
