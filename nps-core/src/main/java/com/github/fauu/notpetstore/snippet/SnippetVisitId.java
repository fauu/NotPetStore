package com.github.fauu.notpetstore.snippet;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

public @Value class SnippetVisitId {

  private @NonNull String snippetId;

  private @NonNull UUID visitorId;

}
