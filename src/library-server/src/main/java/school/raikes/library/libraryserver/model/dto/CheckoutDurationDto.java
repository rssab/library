package school.raikes.library.libraryserver.model.dto;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutDurationDto {
  private Duration checkoutDuration;
}
