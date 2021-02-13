package school.raikes.library.libraryserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data()
public class Copy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "book_id", nullable = false)
  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Book book;

  @Temporal(TemporalType.DATE)
  private Date acquisitionDate;

  @Column(nullable = false)
  private Long barcode;

  @ManyToOne(optional = false)
  private Shelf location;
}
