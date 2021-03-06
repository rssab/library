import React, { useEffect } from "react";

import { UserConsumer } from "../context/UserContext";
import { Redirect } from "react-router-dom";
import CheckoutForm from "../components/checkout/CheckoutForm";
import CheckoutService from "../data/CheckoutService";
import CheckoutConfirmation from "../components/checkout/CheckoutConfirmation";

const CheckoutPage = props => {
  const id = props.match.params.id;
  const checkoutService = CheckoutService(props.requestHandler);

  // Checkout State
  const [
    checkoutConfirmationRedirectLocation,
    setCheckoutConfirmationRedirectionLocation
  ] = React.useState(null);
  const [checkoutResponse, setCheckoutResponse] = React.useState(null);
  const [checkoutDuration, setCheckoutDuration] = React.useState("(loading)");

  // Alert State
  const [errorMessage, setErrorMessage] = React.useState(null);
  const [successMessage, setSuccessMessage] = React.useState(null);

  // Checkout Actions
  const handleCheckoutResponse = response => {
    // Clear any errors that may have occurred.
    setErrorMessage(null);

    // Clear previous Redirect if it still exists.
    setCheckoutConfirmationRedirectionLocation(null);

    // Update State and Redirect.
    setSuccessMessage("Item has been checked out successfully!");
    setCheckoutResponse(response);
    setCheckoutConfirmationRedirectionLocation(response.id);
  };

  const handleError = err => {
    setErrorMessage(err.response);
  };

  const checkoutWithAuth = barcode => {
    checkoutService
      .checkoutWithAuth(barcode)
      .then(handleCheckoutResponse)
      .catch(handleError);
  };

  const checkoutWithoutAuth = (barcode, nuid, pin) => {
    checkoutService
      .checkoutWithoutAuth(barcode)
      .then(handleCheckoutResponse)
      .catch(handleError);
  };

  // Conditionally ask the API for data if no response already loaded.
  useEffect(() => {
    if (id && !checkoutResponse) {
      checkoutService.getCheckout(id).then(setCheckoutResponse);
    }
  }, [checkoutService, id, checkoutResponse, setCheckoutResponse]);

  // Load the checkout duration from the system.
  useEffect(() => {
    checkoutService.getCheckoutDuration().then(setCheckoutDuration);
  }, [setCheckoutDuration, checkoutService]);

  // Render the main component
  let mainComponent;
  if (id) {
    mainComponent = (
      <CheckoutConfirmation
        checkout={checkoutResponse}
        error={errorMessage}
        success={successMessage}
      />
    );
  } else {
    mainComponent = (
      <CheckoutForm
        loggedIn={props.loggedIn}
        error={errorMessage}
        checkoutPeriod={checkoutDuration}
        checkoutWithAuthCallback={checkoutWithAuth}
        checkoutWithoutAuthCallback={checkoutWithoutAuth}
      />
    );
  }

  // Render a redirect if we need to change locations (that is, a checkout has completed successfully).
  let redirect;
  if (checkoutConfirmationRedirectLocation) {
    redirect = (
      <Redirect push to={`/checkout/${checkoutConfirmationRedirectLocation}`} />
    );
  }

  return (
    <div>
      {mainComponent}
      {redirect}
    </div>
  );
};

// Unpack parts of the UserContext for use in the main component.
export default props => {
  return (
    <UserConsumer>
      {value => (
        <CheckoutPage
          {...props}
          requestHandler={value.requestHandler}
          loggedIn={value.isLoggedIn()}
        />
      )}
    </UserConsumer>
  );
};
