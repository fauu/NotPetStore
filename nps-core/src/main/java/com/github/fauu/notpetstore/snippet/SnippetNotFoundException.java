package com.github.fauu.notpetstore.snippet;

import com.github.fauu.notpetstore.common.ResourceNotFoundException;

public class SnippetNotFoundException extends ResourceNotFoundException {

  public SnippetNotFoundException(String snippetId) {
    super("Could not find snippet with id '" + snippetId + "'");
  }

}
