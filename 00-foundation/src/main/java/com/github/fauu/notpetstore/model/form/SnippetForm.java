package com.github.fauu.notpetstore.model.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class SnippetForm {

  @Size(max = 10, message = "{snippetForm.error.titleTooLong}")
  private String title;

  @NotEmpty(message = "{snippetForm.error.contentEmpty}")
  @Size(max = 10, message = "{snippetForm.error.contentTooLong}")
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
