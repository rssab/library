/**
 * Module for handling checkin/checkout actions from the system.
 */
const CheckoutService = requestHandler => {
  /**
   * Triggers a checkout when the provided
   * requestHandler is already authenticated (i.e. a user is
   * already logged in)
   */
  const checkoutWithAuth = async barcode => {
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
      recipient: {
        nuid: "11111111",
        firstName: "Karl",
        lastName: "Admin"
      },
      checkoutDate: "2020/05/25",
      dueDate: "2020/06/08"
    };
  };

  /**
   * Triggers a checkout when the provided requestHandler is NOT authenticated (i.e
   * a user is not currently logged in.)
   */
  const checkoutWithoutAuth = async (barcode, nuid, pin) => {
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
      recipient: {
        nuid: "11111111",
        firstName: "Karl",
        lastName: "Admin"
      },
      checkoutDate: "2020/05/25",
      dueDate: "2020/06/08"
    };
  };

  /**
   * Retrieves a given checkout from the system.
   */
  const getCheckout = async id => {
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

  /**
   * Retrieves the system's currently configured checkout
   * duration in human readable format.
   */
  const getCheckoutDuration = async () => {
    return "two weeks";
  };

  return {
    checkoutWithAuth: checkoutWithAuth,
    checkoutWithoutAuth: checkoutWithoutAuth,
    getCheckout: getCheckout,
    getCheckoutDuration: getCheckoutDuration
  };
};

export default CheckoutService;
