package school.raikes.library.libraryserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
public class CheckoutDurationDto {
  private Duration checkoutDuration;
}
