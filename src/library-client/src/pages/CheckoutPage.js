import React from "react";

import { UserConsumer } from "../context/UserContext";
import CheckoutForm from "../components/checkout/CheckoutForm";
import CheckoutService from "../data/CheckoutService";
import CheckoutConfirmation from "../components/checkout/CheckoutConfirmation";

const CheckoutPage = props => {
  const id = props.match.params.id;
  const checkoutService = CheckoutService(props.requestHandler);

  let mainComponent; 

  if (id) {
    mainComponent = <CheckoutConfirmation checkout={checkoutService.getCheckout(id)}/>
  } else {
    mainComponent = <CheckoutForm loggedIn={props.loggedIn} />
  }

  return (
    <div>
      {mainComponent}
    </div>
  );
};

export default props => {
  return (
    <UserConsumer>
      {value => (
        <CheckoutPage
          {...props}
          requestHandler={value.requestHandler}
          // loggedIn={value.isLoggedIn()}
          loggedIn={false}
        />
      )}
    </UserConsumer>
  );
};
