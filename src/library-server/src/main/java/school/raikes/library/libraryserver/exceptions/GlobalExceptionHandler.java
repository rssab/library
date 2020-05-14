package school.raikes.library.libraryserver.exceptions;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global Handler for handling exceptions thrown throughout the system. By default, it removes the
 * stack trace and exception from sent error responses. Further, for {@link
 * WebApplicationException}s it ensures that the proper HTTP Status Code is sent along with the
 * error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @Bean
  public ErrorAttributes errorAttributes() {

    // Hide exception field in the return object
    return new DefaultErrorAttributes() {

      public Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        errorAttributes.remove("exception");
        errorAttributes.remove("trace");
        return errorAttributes;
      }
    };
  }

  @ExceptionHandler(WebApplicationException.class)
  public void handleCustomException(HttpServletResponse res, WebApplicationException ex)
      throws IOException {
    res.sendError(ex.getStatus().value(), ex.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
  }

  @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
  public void handleMessageUnreadableException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Bad request");
  }

  @ExceptionHandler(Exception.class)
  public void handleException(HttpServletResponse res, Exception ex) throws IOException {
    ex.printStackTrace();
    res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
  }
}
