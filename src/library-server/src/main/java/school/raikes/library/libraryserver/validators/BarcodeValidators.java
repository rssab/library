package school.raikes.library.libraryserver.validators;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BarcodeValidators {
  public static final int CODE_39_CHECKSUM_MODULO = 43;
  public static final char[] CODE_39_CHARACTER_SET = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '.',
    ' ', '$', '/', '+', '%'
  };

  // Map used for Code 39 mod 43 evaluation. Maps the character to its resulting value.
  public static final Map<Character, Integer> CODE_39_CHARACTER_VALUE_MAP =
      IntStream.range(0, CODE_39_CHARACTER_SET.length)
          .boxed()
          .collect(Collectors.toMap((i) -> CODE_39_CHARACTER_SET[i], (i) -> i));

  /**
   * Validates and returns the numeric value of a Code 3 of 9 barcode.
   *
   * @param input the {@link String} value of the Code 3 of 9 barcode.
   * @return the parsed and validated value of the barcode.
   */
  public static int validateCode39Barcode(String input) {
    int checkDigit = CODE_39_CHARACTER_VALUE_MAP.get(input.charAt(input.length() - 1));
    String barcodeDigits = input.substring(0, input.length() - 1);

    int sum = 0;
    for (char c : barcodeDigits.toCharArray()) {
      int result = c - '0';
      if (result < 0 || result > 9) {
        throw new IllegalArgumentException("Invalid Barcode, non-numeric digit.");
      }
      sum += result;
    }

    sum %= 43;

    if (sum != checkDigit) {
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException nfe) {
        throw new IllegalArgumentException("Invalid Barcode, failed to parse barcode.");
      }
    }

    return Integer.parseInt(barcodeDigits);
  }
}
