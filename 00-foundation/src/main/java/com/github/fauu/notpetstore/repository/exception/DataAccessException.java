package com.github.fauu.notpetstore.repository.exception;

public class DataAccessException extends RuntimeException {

  public DataAccessException(String msg) {
    super(msg);
  }

  public DataAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
