package school.raikes.library.libraryserver.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Checkout {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Copy copy;

  @ManyToOne
  private LibraryAccount recipient;

  @Temporal(TemporalType.TIMESTAMP)
  private Date checkoutDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date checkinDate;
}
