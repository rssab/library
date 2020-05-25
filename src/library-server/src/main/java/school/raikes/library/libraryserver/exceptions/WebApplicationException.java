package school.raikes.library.libraryserver.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Custom {@link RuntimeException} that is used throughout the application for errors and
 * encapsulates a HTTP Status in addition to the error message and exception.
 */
public class WebApplicationException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "An error occurred.";

  private final HttpStatus status;
  private final String message;

  public WebApplicationException(String message, HttpStatus status, Exception inner) {
    super(message, inner);
    this.status = status;
    this.message = message;
  }

  public WebApplicationException() {
    this(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, null);
  }

  public WebApplicationException(String message, Exception inner) {
    this(message, HttpStatus.INTERNAL_SERVER_ERROR, inner);
  }

  public WebApplicationException(String message) {
    this(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
  }

  public WebApplicationException(String message, HttpStatus status) {
    this(message, status, null);
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
