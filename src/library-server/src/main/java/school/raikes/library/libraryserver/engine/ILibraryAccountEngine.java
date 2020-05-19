package school.raikes.library.libraryserver.engine;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;
import school.raikes.library.libraryserver.model.entity.Role;

/**
 * Engine interface for managing {@link LibraryAccount}s. Extends the {@link UserDetailsService} so
 * that Library Accounts can operate as the user accounts for the system and serve as the basis for
 * authorization and authentication.
 */
public interface ILibraryAccountEngine extends UserDetailsService {
  Iterable<LibraryAccount> findAll();

  Iterable<LibraryAccount> findAllWithRole(Role role);

  Optional<LibraryAccount> findByNuid(String nuid);

  LibraryAccount findById(Long id);

  LibraryAccount save(LibraryAccount account);

  void delete(LibraryAccount libraryAccount);

  String login(String nuid, String pin);

  String register(LibraryAccount libraryAccount);

  LibraryAccount whoami(HttpServletRequest request);
}
