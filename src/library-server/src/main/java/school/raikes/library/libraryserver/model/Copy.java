package school.raikes.library.libraryserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

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
  private Book book;

  @Temporal(TemporalType.DATE)
  private Date acquisitionDate;

  @Column private String barcode;

  @ManyToOne private Shelf location;

}
