package com.github.fauu.notpetstore.repository.exception;

public class DataAccessException extends RuntimeException {

  public DataAccessException(final String msg) {
    super(msg);
  }

  public DataAccessException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

}
