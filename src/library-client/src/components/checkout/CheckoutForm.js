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

import {
  validateNuid,
  validatePin,
  validateCode39Barcode
} from "../../common/validators";

const CheckoutForm = props => {
  const [nuidValidationError, setNuidValidationError] = React.useState(false);
  const [pinValidationError, setPinValidationError] = React.useState(false);
  const [barcodeValidationError, setBarcodeValidationError] = React.useState(
    false
  );

  const [nuid, setNuid] = React.useState();
  const [pin, setPin] = React.useState();
  const [barcode, setBarcode] = React.useState();

  const handleSubmit = event => {
    event.preventDefault();

    let validBarcode;
    let parsedBarcode;
    if (barcode !== undefined) {
      if (validateCode39Barcode(barcode)) {
        // If it is CODE39, strip of the check digit.
        parsedBarcode = barcode.substring(0, barcode.length - 1);
        validBarcode = true;
      } else {
        // If it isn't CODE39, attempt to turn it into a number, if that succeeds, assume OK.
        parsedBarcode = Number(barcode);
        validBarcode = !isNaN(parsedBarcode);
      }
    } else {
      validBarcode = false;
    }

    if (!props.loggedIn) {
      const validNuid = validateNuid(nuid);
      const validPin = validatePin(pin);

      if (validPin && validNuid && validBarcode) {
        props.checkoutWithoutAuthCallback(parsedBarcode, nuid, pin);
      } else {
        setNuidValidationError(!validNuid);
        setPinValidationError(!validPin);
        setBarcodeValidationError(!validBarcode);
      }
    } else {
      if (validBarcode) {
        props.checkoutWithAuthCallback(parsedBarcode);
      } else {
        setBarcodeValidationError(!validBarcode);
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
              id="checkout-nuid"
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
              id="checkout-pin"
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
      {props.error && <Alert color="danger">{props.error}</Alert>}
      <div className="pt-2">
        <p className="mx-0">
          Checkout a book by entering the information below. Books can be
          checked out for <strong>{props.checkoutPeriod}</strong> at a time.
        </p>
        <Form onSubmit={handleSubmit}>
          {renderLoginFields(props.loggedIn)}
          <FormGroup>
            <Label for="barcode">Item Barcode</Label>
            <Input
              name="barcode"
              id="barcode"
              onChange={e => setBarcode(e.target.value)}
              invalid={barcodeValidationError}
            />
            <FormFeedback>
              That is not a valid barcode. Please try scanning the item again,
              or enter the code from the barcode manually in this field.
            </FormFeedback>
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
