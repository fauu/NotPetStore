package com.github.fauu.notpetstore.web.feedback;

public enum UserActionFeedback {

  SNIPPET_ADD_FORM_INVALLID(Type.FAILURE, "snippetAddFormInvalid"),
  SNIPPET_ADD_SUCCESS(Type.SUCCESS, "snippetAddSuccess");

  private Type type;

  private String code;

  UserActionFeedback(Type type, String code) {
    this.type = type;
    this.code = code;
  }

  public Type getType() {
    return type;
  }

  public String getCode() {
    return code;
  }

  public enum Type {
    SUCCESS("success"),
    FAILURE("failure");

    private String code;

    Type(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

}
