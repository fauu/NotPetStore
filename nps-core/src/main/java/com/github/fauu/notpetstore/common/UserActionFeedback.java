package com.github.fauu.notpetstore.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserActionFeedback {

  SNIPPET_ADD_FORM_INVALLID
      (Type.FAILURE, "snippetAddFormInvalid"),

  SNIPPET_ADD_SUCCESS
      (Type.SUCCESS, "snippetAddSuccess"),

  SNIPPET_PERFORM_OWNER_ACTION_PASSWORD_INVALID
      (Type.FAILURE, "snippetPerformOwnerActionPasswordInvalid"),

  SNIPPET_DELETE_SUCCESS
      (Type.SUCCESS, "snippetDeleteSuccess");

  private @NonNull Type type;

  private @NonNull String code;

  @RequiredArgsConstructor
  @Getter
  public enum Type {
    SUCCESS("success"),
    FAILURE("failure");

    private final @NonNull String code;
  }

}
