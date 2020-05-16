package school.raikes.library.libraryserver.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import school.raikes.library.libraryserver.exceptions.WebApplicationException;

/**
 * Filter that runs before the standard Spring Boot Auth filter and sets the authentication on the
 * request based on the passed JWT. Issues an exception if no JWT is found, triggering an error.
 */
public class JwtTokenFilter extends GenericFilterBean {
  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {

    String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (WebApplicationException ex) {
      HttpServletResponse response = (HttpServletResponse) res;
      response.sendError(ex.getStatus().value(), ex.getMessage());
      return;
    }

    filterChain.doFilter(req, res);
  }
}
