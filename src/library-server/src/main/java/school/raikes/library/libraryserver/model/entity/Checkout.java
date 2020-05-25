package school.raikes.library.libraryserver.model.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Checkout {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private Copy copy;

  @ManyToOne(optional = false)
  private LibraryAccount recipient;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date checkoutDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date checkinDate;
}
