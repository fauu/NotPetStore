package com.github.fauu.notpetstore.service.exception;

public class ServiceException extends RuntimeException {

  public ServiceException(final String msg) {
    super(msg);
  }

  public ServiceException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

}
