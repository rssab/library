package school.raikes.library.libraryserver.utilities;

import org.springframework.security.core.Authentication;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;
import school.raikes.library.libraryserver.security.enums.Role;

/**
 * Utility class containing functions for checking {@link
 * org.springframework.security.core.Authentication} objects.
 */
public final class AuthenticationUtilities {

  /**
   * Checks an {@link Authentication} to see if a given user has the authority corresponding to the
   * {@link Role#ADMIN} role or not.
   *
   * @return True if the given authentication has the authority, false otherwise.
   */
  public static boolean hasAdminAuthority(Authentication authentication) {
    if (authentication == null) {
      return false;
    }
    return authentication.getAuthorities().stream()
        .anyMatch((authority) -> authority.getAuthority().equals(Role.ADMIN.getAuthority()));
  }

  /** Returns the NUID of a given {@link LibraryAccount} from the {@link Authentication} object. */
  public static String getNuid(Authentication authentication) {
    // Safe Cast since we create the authentication.
    if (authentication == null) {
      return null;
    }
    LibraryAccount account = (LibraryAccount) authentication.getPrincipal();
    return account.getNuid();
  }
}
