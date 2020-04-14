package school.raikes.library.libraryserver.model.dto;

import lombok.Data;

@Data
public class NuidPinAuthorizationDto {
  private String nuid;
  private String pin;
}
