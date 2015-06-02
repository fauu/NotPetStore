package com.github.fauu.notpetstore.snippet;

import com.github.fauu.notpetstore.common.ResourceNotFoundException;

public class RequestedSnippetDeletedException extends ResourceNotFoundException {

  public RequestedSnippetDeletedException(String snippetId) {
    super("Snippet with id '" + snippetId + "' has been deleted");
  }

}
