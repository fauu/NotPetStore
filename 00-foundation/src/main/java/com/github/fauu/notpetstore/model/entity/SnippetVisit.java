package com.github.fauu.notpetstore.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class SnippetVisit {

  private String snippetId;

  private UUID visitorId;

  private LocalDateTime dateTime;

  public String getSnippetId() {
    return snippetId;
  }

  public void setSnippetId(String snippetId) {
    this.snippetId = snippetId;
  }

  public UUID getVisitorId() {
    return visitorId;
  }

  public void setVisitorId(UUID visitorId) {
    this.visitorId = visitorId;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SnippetVisit that = (SnippetVisit) o;

    if (!snippetId.equals(that.snippetId)) return false;
    if (!visitorId.equals(that.visitorId)) return false;
    return dateTime.equals(that.dateTime);

  }

  @Override
  public int hashCode() {
    int result = snippetId.hashCode();
    result = 31 * result + visitorId.hashCode();
    result = 31 * result + dateTime.hashCode();
    return result;
  }

}
