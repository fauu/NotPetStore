package com.github.fauu.notpetstore.model.entity;

public class Snippet {

  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Snippet{" +
           "content='" + content + '\'' +
           '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Snippet snippet = (Snippet) o;

    return content.equals(snippet.content);
  }

  @Override
  public int hashCode() {
    return content.hashCode();
  }

}
