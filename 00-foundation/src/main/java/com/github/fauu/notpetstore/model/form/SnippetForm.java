package com.github.fauu.notpetstore.model.form;

import com.github.fauu.notpetstore.model.entity.Snippet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SnippetForm {

  @Size(max = 80, message = "{snippetForm.error.titleTooLong}")
  private String title;

  @Size(min = 140, max = 5000, message = "{snippetForm.error.contentBadLength}")
  private String content;

  @NotNull
  private Snippet.Visibility visibility;

  private Snippet.Visibility[] visibilityValues = Snippet.Visibility.values();

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

  public Snippet.Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Snippet.Visibility visibility) {
    this.visibility = visibility;
  }

  public Snippet.Visibility[] getVisibilityValues() {
    return visibilityValues;
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

}
