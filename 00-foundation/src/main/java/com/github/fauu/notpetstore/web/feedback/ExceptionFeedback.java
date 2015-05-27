package com.github.fauu.notpetstore.web.feedback;

public enum ExceptionFeedback {

  SERVER_ERROR_DEFAULT(true, "500", "serverErrorDefault"),
  BAD_REQUEST_DEFAULT(false, "400", "badRequestDefault"),
  PAGE_NOT_FOUND_DEFAULT(false, "404", "pageNotFoundDefault"),
  REQUESTED_SNIPPET_DELETED(false, "404", "requestedSnippetDeleted");

  private boolean severe;

  private String statusCode;

  private String code;

  ExceptionFeedback(boolean severe, String statusCode, String code) {
    this.severe = severe;
    this.statusCode = statusCode;
    this.code = code;
  }

  public boolean isSevere() {
    return severe;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public String getCode() {
    return code;
  }

}
