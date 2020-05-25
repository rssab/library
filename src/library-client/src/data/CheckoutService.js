/**
 * Module for handling checkin/checkout actions from the system.
 */
const CheckoutService = requestHandler => {
  const checkoutWithAuth = barcode => {
    return {
      id: 123,
      book: {
        isbn: "12345678",
        title: "Matt Vav: ",
        subtitle: "The Untold Story",
        editon: 1,
        authors: [
          {
            firstName: "Karl",
            lastName: "Shaffer"
          }
        ]
      },
      recipient: {},
      checkoutDate: "2020/05/25",
      dueDate: "2020/06/08"
    };
  };

  const checkoutWithoutAuth = (barcode, nuid, pin) => {
    return {
      id: 123,
      book: {
        isbn: "12345678",
        title: "Matt Vav: ",
        subtitle: "The Untold Story",
        editon: 1,
        authors: [
          {
            firstName: "Karl",
            lastName: "Shaffer"
          }
        ]
      },
      recipient: {},
      checkoutDate: "2020/05/25",
      dueDate: "2020/06/08"
    };
  };

  const getCheckout = id => {
    return {
      id: id,
      book: {
        isbn: "12345678",
        title: "Matt Vav: ",
        subtitle: "The Untold Story",
        editon: 1,
        authors: [
          {
            firstName: "Karl",
            lastName: "Shaffer"
          }
        ]
      },
      recipient: {
        nuid: "11111111",
        firstName: "Karl",
        lastName: "Admin"
      },
      checkoutDate: "2020/05/25",
      dueDate: "2020/06/08"
    };
  };

  return {
    checkoutWithAuth: checkoutWithAuth,
    checkoutWithoutAuth: checkoutWithoutAuth,
    getCheckout: getCheckout
  };
};

export default CheckoutService;
