import React from "react";

import { UserConsumer } from "../context/UserContext";
import CheckoutForm from "../components/checkout/CheckoutForm";

const CheckoutPage = props => {
  console.log("Here!");

  return (
    <div>
      <h1 className="display-4">Checkout</h1>
      <CheckoutForm />
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
          loggedIn={value.isLoggedIn()}
        />
      )}
    </UserConsumer>
  );
};
