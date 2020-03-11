package school.raikes.library.libraryserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import school.raikes.library.libraryserver.engine.ILibraryAccountEngine;
import school.raikes.library.libraryserver.exceptions.WebApplicationException;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;
import school.raikes.library.libraryserver.model.entity.Role;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Provider of JWT authorization tokens that manages, issues, and validates JWTs sent to the system.
 */
@Component
public class JwtTokenProvider {

  public static final String AUTH_HEADER = "Authorization";
  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  public static final String INVALID_JWT_MESSAGE = "Expired or Invalid JWT token.";
  public static final String CLAIMS_ROLE_KEY = "auth";

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt/token.expire-length:3600000}")
  private long validityInMilliseconds;

  private ILibraryAccountEngine libraryAccountEngine;

  @Autowired
  public JwtTokenProvider(ILibraryAccountEngine libraryAccountEngine) {
    this.libraryAccountEngine = libraryAccountEngine;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String nuid, Role role) {

    Claims claims = Jwts.claims().setSubject(nuid);
    claims.put(CLAIMS_ROLE_KEY, new SimpleGrantedAuthority(role.getName()));

    LibraryAccount account = libraryAccountEngine.findByNuid(nuid);

    Map<String, String> userDetails = new HashMap<>();
    userDetails.put("firstName", account.getFirstName());
    userDetails.put("lastName", account.getLastName());

    claims.put("account", userDetails);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = libraryAccountEngine.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return (token != null)
        ? Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
        : null;
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTH_HEADER);
    if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
      return bearerToken.substring(BEARER_TOKEN_PREFIX.length());
    }

    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new WebApplicationException(INVALID_JWT_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
  }
}
