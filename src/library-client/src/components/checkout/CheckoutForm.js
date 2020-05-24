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

const CheckoutForm = props => {
  return (
    <div className="pt-5">
      <p className="mx-0">
        Checkout a book by entering the information below. Books can be checked
        out for <strong>two weeks</strong> at a time.
      </p>
      <Form>
        <FormGroup>
            <Label for="nuid">NUID</Label>
            <Input type="text" name="nuid" id="nuid"></Input>
            <FormFeedback>That is not a valid NUID (should be 8 digits).</FormFeedback>
        </FormGroup>
        <FormGroup>
            <Label for="pin">PIN</Label>
            <Input type="password" name="pin" id="pin"/>
            <FormFeedback>That is not a valid PIN (should be numeric 4-8 digits).</FormFeedback>
        </FormGroup>
        <FormGroup>
            <Label for="barcode">Item Barcode</Label>
            <Input name="barcode" id="barcode" />
        </FormGroup>
        <FormGroup className="mt-5">
            <Button color="primary" block>Checkout</Button>
        </FormGroup>
      </Form>
    </div>
  );
};

export default CheckoutForm;
