/**
 * Contains shared validation methods.
 */

const NUMBERS_REGEX = /^[0-9]+$/;
const NUID_LENGTH = 8;
const PIN_MINIMUM_LENGTH = 4;
const PIN_MAXIMUM_LENGTH = 8;

const validateNuid = nuid => {
  return (
    nuid !== undefined &&
    nuid.length === NUID_LENGTH &&
    nuid.match(NUMBERS_REGEX)
  );
};

const validatePin = pin => {
  return (
    pin !== undefined &&
    pin.length >= PIN_MINIMUM_LENGTH &&
    pin.length <= PIN_MAXIMUM_LENGTH &&
    pin.match(NUMBERS_REGEX)
  );
};

export { validateNuid, validatePin };
