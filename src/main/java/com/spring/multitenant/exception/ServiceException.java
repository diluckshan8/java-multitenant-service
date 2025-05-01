package com.spring.multitenant.exception;

public class ServiceException extends Exception {

  /**
   * Default serial Version UID.
   */
  private static final long serialVersionUID = 1L;
  private String errorCode;

  public String getErrorCode() {
    return errorCode;
  }

  public ServiceException(String message, int errorCode) {
    super(message);
    this.errorCode =  String.valueOf(errorCode);
  }

  public ServiceException(String message, Throwable cause, ErrorCodes errorCode) {
    super(message, cause);
    this.errorCode = errorCode.toString();
  }

  public ServiceException(String message, Throwable cause, String errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public ServiceException(String message, Throwable cause, int errorCode) {
    super(message, cause);
    this.errorCode = String.valueOf(errorCode);
  }

}
