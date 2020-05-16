package school.raikes.library.libraryserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Copy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonIgnore
  @ToString.Exclude
  private Book book;

  @Temporal(TemporalType.DATE)
  private Date acquisitionDate;

  @Column private Long barcode;

  @ManyToOne private Shelf location;
}
