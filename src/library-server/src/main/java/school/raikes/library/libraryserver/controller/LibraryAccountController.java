package school.raikes.library.libraryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.raikes.library.libraryserver.engine.ILibraryAccountEngine;
import school.raikes.library.libraryserver.engine.IRoleEngine;
import school.raikes.library.libraryserver.model.dto.JwtAuthorizationDto;
import school.raikes.library.libraryserver.model.dto.NuidPinAuthorizationDto;

/**
 * Controller class for the {@link school.raikes.library.libraryserver.model.entity.LibraryAccount}
 * which also contains the handling of user login and registration.
 */
@RestController
public class LibraryAccountController extends ApiController {

  private static final String LIBRARY_ACCOUNT_RESOURCE_PATH = "/accounts";
  private static final String LOG_IN_PATH = "/login";

  private ILibraryAccountEngine libraryAccountEngine;
  private IRoleEngine roleEngine;

  @Autowired
  public LibraryAccountController(
      ILibraryAccountEngine libraryAccountEngine, IRoleEngine roleEngine) {
    this.libraryAccountEngine = libraryAccountEngine;
    this.roleEngine = roleEngine;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtAuthorizationDto> login(
      @RequestBody NuidPinAuthorizationDto nuidPinAuthorizationDto) {
    String token =
        libraryAccountEngine.login(
            nuidPinAuthorizationDto.getNuid(), nuidPinAuthorizationDto.getPin());
    return new ResponseEntity<>(new JwtAuthorizationDto(token), HttpStatus.OK);
  }
}
