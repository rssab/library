package school.raikes.library.libraryserver.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Checkout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Copy copy;

  @ManyToOne private LibraryAccount recipient;

  @Temporal(TemporalType.TIMESTAMP)
  private Date checkoutDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date checkinDate;
}
