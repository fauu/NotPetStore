package com.github.fauu.notpetstore.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionFeedback {

  SERVER_ERROR_DEFAULT
      (true, "500", "serverErrorDefault"),

  BAD_REQUEST_DEFAULT
      (false, "400", "badRequestDefault"),

  PAGE_NOT_FOUND_DEFAULT
      (false, "404", "pageNotFoundDefault"),

  REQUESTED_SNIPPET_DELETED
      (false, "404", "requestedSnippetDeleted");

  private @NonNull boolean severe;

  private @NonNull String statusCode;

  private @NonNull String code;

}
