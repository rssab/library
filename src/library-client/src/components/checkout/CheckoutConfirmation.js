import React from "react";

import {
    Table
} from 'reactstrap';

const CheckoutConfirmation = props => {
  
  const book = `${props.checkout.book.title} ${props.checkout.book.subtitle}`
  const recipient = `${props.checkout.recipient.firstName} ${props.checkout.recipient.lastName}`
  return (
    <div>
      <h1 className="display-4">Checkout Confirmation</h1>
      <div>
        <p>
          Thanks for checkout out a book! You can find the details of your
          checkout below.
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
};

export default CheckoutConfirmation;
