/**
 * Contains shared validation methods.
 */

const NUMBERS_REGEX = /^[0-9]+$/;
const NUID_LENGTH = 8;
const PIN_MINIMUM_LENGTH = 4;
const PIN_MAXIMUM_LENGTH = 8;

const CODE39_CHARACTER_SET = {
  "0": 0,
  "1": 1,
  "2": 2,
  "3": 3,
  "4": 4,
  "5": 5,
  "6": 6,
  "7": 7,
  "8": 8,
  "9": 9,
  A: 10,
  B: 11,
  C: 12,
  D: 13,
  E: 14,
  F: 15,
  G: 16,
  H: 17,
  I: 18,
  J: 19,
  K: 20,
  L: 21,
  M: 22,
  N: 23,
  O: 24,
  P: 25,
  Q: 26,
  R: 27,
  S: 28,
  T: 29,
  U: 30,
  V: 31,
  W: 32,
  X: 33,
  Y: 34,
  Z: 35,
  "-": 36,
  ".": 37,
  " ": 38,
  $: 39,
  "/": 40,
  "+": 41,
  "%": 42
};

const CODE39_MODULO_VALUE = 43;

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

const validateCode39Barcode = barcode => {
  let checkDigit = barcode.charAt(barcode.length - 1);
  let barcodeValue = barcode.substring(0, barcode.length - 1);

  let sum = 0;
  for (let i = 0; i < barcodeValue.length; i++) {
    let currentCharacterValue = parseInt(barcodeValue.charAt(i));
    if (isNaN(currentCharacterValue)) {
      return false;
    }
    sum += currentCharacterValue;
  }

  if (sum % CODE39_MODULO_VALUE === CODE39_CHARACTER_SET[checkDigit]) {
    return true;
  }

  return false;
};

export { validateNuid, validatePin, validateCode39Barcode };
