package com.github.fauu.notpetstore.common;

// TODO: Revisit
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable ex) {
    super(message, ex);
  }

}
