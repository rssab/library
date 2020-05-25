package school.raikes.library.libraryserver.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/** Tests for the {@link BarcodeValidators} class. */
public class BarcodeValidatorsTests {

  @ParameterizedTest
  @CsvSource({"3339, 333", "172A, 172", "00193D, 193", "0196G, 196", "002079, 207", "000244A, 244"})
  public void testValidateCode39ProperlyValidates(String input, int expected) {
    Assertions.assertEquals(expected, BarcodeValidators.validateCode39Barcode(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"ABCDE", "3S345", "0333J", "000332M"})
  public void testValidateCode39FailsOnBadBarcode(String input) {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> BarcodeValidators.validateCode39Barcode(input),
        String.format("Expected Exception Thrown for Input (%s) but None was Thrown", input));
  }
}
