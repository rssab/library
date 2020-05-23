package school.raikes.library.libraryserver.engine;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.ILibraryAccountAccessor;
import school.raikes.library.libraryserver.exceptions.WebApplicationException;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;
import school.raikes.library.libraryserver.model.entity.Role;
import school.raikes.library.libraryserver.security.JwtTokenProvider;

/** Basic implementation of the {@link ILibraryAccountEngine}. */
@Service
public class LibraryAccountEngine implements ILibraryAccountEngine {

  public static final String AUTH_FAILURE_MESSAGE = "Invalid NUID or PIN.";
  private static final String EXISTING_USER_FAILURE_MESSAGE =
      "User with provided NUID already exists.";
  private static final String NUID_NOT_FOUND_FAILURE_MESSAGE = "NUID not found.";

  private final ILibraryAccountAccessor libraryAccountAccessor;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public LibraryAccountEngine(
      ILibraryAccountAccessor libraryAccountAccessor,
      PasswordEncoder passwordEncoder,
      @Lazy JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager) {
    this.libraryAccountAccessor = libraryAccountAccessor;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Iterable<LibraryAccount> findAll() {
    return libraryAccountAccessor.findAll();
  }

  @Override
  public Iterable<LibraryAccount> findAllWithRole(Role role) {
    return libraryAccountAccessor.findAllByRole(role);
  }

  @Override
  public Optional<LibraryAccount> findByNuid(String nuid) {
    return libraryAccountAccessor.findByNuid(nuid);
  }

  @Override
  public LibraryAccount findById(Long id) {
    Optional<LibraryAccount> account = libraryAccountAccessor.findById(id);

    return account.orElse(null);
  }

  @Override
  public LibraryAccount save(LibraryAccount account) {
    Optional<LibraryAccount> current = findByNuid(account.getNuid());

    if (current.isPresent()) {
      if (!current.get().getPassword().equals(account.getPassword())) {
        account.setPin(passwordEncoder.encode(account.getPin()));
      }
    }

    return libraryAccountAccessor.save(account);
  }

  @Override
  public void delete(LibraryAccount libraryAccount) {
    libraryAccountAccessor.delete(libraryAccount);
  }

  @Override
  public String login(String nuid, String pin) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nuid, pin));
      return jwtTokenProvider.createToken(
          nuid, libraryAccountAccessor.findByNuid(nuid).get().getRole());
    } catch (AuthenticationException e) {
      throw new WebApplicationException(AUTH_FAILURE_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public String register(LibraryAccount libraryAccount) {
    if (!libraryAccountAccessor.existsByNuid(libraryAccount.getNuid())) {
      libraryAccount.setPin(passwordEncoder.encode(libraryAccount.getPassword()));
      libraryAccountAccessor.save(libraryAccount);
      return jwtTokenProvider.createToken(libraryAccount.getUsername(), libraryAccount.getRole());
    } else {
      throw new WebApplicationException(EXISTING_USER_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public LibraryAccount whoami(HttpServletRequest request) {
    return libraryAccountAccessor
        .findByNuid(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)))
        .get();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<LibraryAccount> account = findByNuid(username);

    if (account.isPresent()) {
      return account.get();
    } else {
      throw new UsernameNotFoundException(NUID_NOT_FOUND_FAILURE_MESSAGE);
    }
  }
}
