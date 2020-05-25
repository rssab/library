import React from "react";

import {
  Alert,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
  FormFeedback
} from "reactstrap";

import { validateNuid, validatePin } from "../../common/validators";

const CheckoutForm = props => {
  const [nuidValidationError, setNuidValidationError] = React.useState(false);
  const [pinValidationError, setPinValidationError] = React.useState(false);

  const [nuid, setNuid] = React.useState();
  const [pin, setPin] = React.useState();
  const [barcode, setBarcode] = React.useState();

  const handleSubmit = event => {
    event.preventDefault();

    if (!props.loggedIn) {
      const validNuid = validateNuid(nuid);
      const validPin = validatePin(pin);

      if (validPin && validNuid) {
        //TODO: Handle Checkout Action
      } else {
        setNuidValidationError(!validNuid);
        setPinValidationError(!validPin);
      }
    }
  };

  /**
   * Render login fields so a user can enter their
   * values as part of the checkout process.
   */
  const renderLoginFields = isLoggedIn => {
    if (!isLoggedIn) {
      return (
        <span>
          <FormGroup>
            <Label for="nuid">NUID</Label>
            <Input
              type="text"
              name="nuid"
              id="nuid"
              onChange={e => setNuid(e.target.value)}
              invalid={nuidValidationError}
            />
            <FormFeedback>
              That is not a valid NUID (should be 8 digits).
            </FormFeedback>
          </FormGroup>
          <FormGroup>
            <Label for="pin">PIN</Label>
            <Input
              type="password"
              name="pin"
              id="pin"
              onChange={e => setPin(e.target.value)}
              invalid={pinValidationError}
            />
            <FormFeedback>
              That is not a valid PIN (should be numeric 4-8 digits).
            </FormFeedback>
          </FormGroup>
        </span>
      );
    } else {
      return null;
    }
  };

  return (
    <div>
      <h1 className="display-4">Checkout</h1>
      <div className="pt-2">
        <p className="mx-0">
          Checkout a book by entering the information below. Books can be
          checked out for <strong>two weeks</strong> at a time.
        </p>
        <Form onSubmit={handleSubmit}>
          {renderLoginFields(props.loggedIn)}
          <FormGroup>
            <Label for="barcode">Item Barcode</Label>
            <Input
              name="barcode"
              id="barcode"
              onChange={e => setBarcode(e.target.value)}
            />
          </FormGroup>
          <FormGroup className="mt-5">
            <Button color="primary" block>
              Checkout
            </Button>
          </FormGroup>
        </Form>
      </div>
    </div>
  );
};

export default CheckoutForm;
