import React from "react";

import { Alert, Table } from "reactstrap";

const CheckoutConfirmation = props => {
  let content;
  if (props.checkout) {
    const book = `${props.checkout.book.title} ${props.checkout.book.subtitle}`;
    const recipient = `${props.checkout.recipient.firstName} ${props.checkout.recipient.lastName}`;
    content = (
      <div>
        <h1 className="display-4">Checkout Confirmation</h1>
        {props.success && <Alert color="success">{props.success}</Alert>}
        {props.error && <Alert color="danger">{props.error}</Alert>}
        <div>
          <p>
            Thanks for checking out a book! You can find the details of your
            checkout below. Please note the due date below and try to get your
            book checked back in on time.
          </p>
          <Table size="sm" borderless>
            <tbody>
              <tr>
                <th scope="row">Book:</th>
                <td>{book}</td>
              </tr>
              <tr>
                <th scope="row">Recipient:</th>
                <td>{recipient}</td>
              </tr>
              <tr>
                <th scope="row">Checkout Date:</th>
                <td>{props.checkout.checkoutDate}</td>
              </tr>
              <tr>
                <th scope="row">Due Date:</th>
                <td>{props.checkout.dueDate}</td>
              </tr>
            </tbody>
          </Table>
        </div>
      </div>
    );
  } else {
    content = (
      <div className="mt-5 text-center">
        <p>Loading... Please be patient.</p>
      </div>
    );
  }

  return <div>{content}</div>;
};

export default CheckoutConfirmation;
