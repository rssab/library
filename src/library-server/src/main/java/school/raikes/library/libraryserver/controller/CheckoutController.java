package school.raikes.library.libraryserver.controller;

import java.time.Duration;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import school.raikes.library.libraryserver.engine.ICheckoutEngine;
import school.raikes.library.libraryserver.engine.ILibraryAccountEngine;
import school.raikes.library.libraryserver.exceptions.WebApplicationException;
import school.raikes.library.libraryserver.model.dto.CheckoutDto;
import school.raikes.library.libraryserver.model.dto.CheckoutDurationDto;
import school.raikes.library.libraryserver.model.entity.Checkout;
import school.raikes.library.libraryserver.utilities.AuthenticationUtilities;

/**
 * Controller class for the {@link Checkout} objects which handles both the checkout and checkin
 * actions that users would do.
 */
@RestController
public class CheckoutController extends ApiController {
  public static final String CHECKOUT_PATH = "/checkout";
  public static final String CHECKIN_PATH = "/checkin";
  public static final String CHECKOUT_DURATION_PATH = CHECKOUT_PATH + "/duration";

  private final ICheckoutEngine checkoutEngine;
  private final ILibraryAccountEngine libraryAccountEngine;

  @Autowired
  public CheckoutController(ICheckoutEngine checkoutEngine, ILibraryAccountEngine libraryAccountEngine) {
    this.checkoutEngine = checkoutEngine;
    this.libraryAccountEngine = libraryAccountEngine;
  }

  @GetMapping(CHECKOUT_DURATION_PATH)
  public CheckoutDurationDto getCheckoutDuration() {
    return new CheckoutDurationDto(this.checkoutEngine.getCheckoutDuration());
  }

  @GetMapping(CHECKOUT_PATH + "/{id}")
  @PreAuthorize("isAuthenticated()")
  public Checkout getCheckout(Authentication authentication, @PathVariable("id") long id) {
    Optional<Checkout> checkoutQueryResult = checkoutEngine.findById(id);
    if (!checkoutQueryResult.isPresent()) {
      throw new WebApplicationException(
          String.format("Unable to find Checkout with ID: %d", id), HttpStatus.NOT_FOUND);
    }

    Checkout checkout = checkoutQueryResult.get();

    if (checkout.getRecipient().getNuid().equals(AuthenticationUtilities.getNuid(authentication))
        || AuthenticationUtilities.hasAdminAuthority(authentication)) {
      return checkout;
    }

    throw new WebApplicationException(
        "You are not authorized to view this checkout.", HttpStatus.UNAUTHORIZED);
  }

  @PostMapping(CHECKOUT_PATH)
  public Checkout checkout(Authentication authentication, @RequestBody CheckoutDto checkoutDto) {
    if (authentication == null) {
      if (checkoutDto.getNuid() == null || checkoutDto.getPin() == null) {
        throw new WebApplicationException("You must be logged in or provide credentials to checkout an item", HttpStatus.BAD_REQUEST);
      }
    }

    if (checkoutDto.getBarcode() == null) {
      throw new WebApplicationException("You must provide a barcode for an item to checkout.", HttpStatus.BAD_REQUEST);
    }

    String authNuid = AuthenticationUtilities.getNuid(authentication);
    String checkoutNuid = checkoutDto.getNuid();
    String checkoutPin = checkoutDto.getPin();
    String barcode = checkoutDto.getBarcode();

    // Allow only administrators to check out for another user.
    if (authNuid != null && !authNuid.equals(checkoutNuid) && !AuthenticationUtilities.hasAdminAuthority(authentication)) {
      throw new WebApplicationException("You cannot checkout an item for another user.", HttpStatus.FORBIDDEN);
    }

    if (authentication == null) {
      this.libraryAccountEngine.login(checkoutNuid, checkoutPin);
      return this.checkoutEngine.checkout(checkoutNuid, barcode);
    }

    if (checkoutNuid != null) {
      return this.checkoutEngine.checkout(checkoutNuid, barcode);
    } else {
      return this.checkoutEngine.checkout(authNuid, barcode);
    }
  }
}
