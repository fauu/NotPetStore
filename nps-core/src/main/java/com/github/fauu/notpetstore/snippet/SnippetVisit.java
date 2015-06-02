package com.github.fauu.notpetstore.snippet;

import com.github.fauu.notpetstore.common.Identifiable;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public @Data class SnippetVisit implements Identifiable<SnippetVisitId> {

  private @NonNull SnippetVisitId id;

  private @NonNull LocalDateTime dateTime;

  public SnippetVisit(@NonNull String snippetId,
                      @NonNull UUID visitorId,
                      @NonNull LocalDateTime dateTime) {
    this.id = new SnippetVisitId(snippetId, visitorId);
    this.dateTime = dateTime;
  }

  public String getSnippetId() {
    return id.getSnippetId();
  }

  public UUID getVisitorId() {
    return id.getVisitorId();
  }

}
