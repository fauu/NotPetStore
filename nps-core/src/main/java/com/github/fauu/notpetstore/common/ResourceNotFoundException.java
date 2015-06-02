package com.github.fauu.notpetstore.common;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
