package school.raikes.library.libraryserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthorizationDto {
  private String token;
}
