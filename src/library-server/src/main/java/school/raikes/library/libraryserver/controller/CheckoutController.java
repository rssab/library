package school.raikes.library.libraryserver.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.raikes.library.libraryserver.engine.ICheckoutEngine;
import school.raikes.library.libraryserver.exceptions.WebApplicationException;
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

  @Autowired
  public CheckoutController(ICheckoutEngine checkoutEngine) {
    this.checkoutEngine = checkoutEngine;
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
}
