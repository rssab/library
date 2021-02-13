package school.raikes.library.libraryserver.model.dto;

import lombok.Data;

@Data
public class CheckoutDto {
  private String nuid;
  private String pin;
  private String barcode;
}
