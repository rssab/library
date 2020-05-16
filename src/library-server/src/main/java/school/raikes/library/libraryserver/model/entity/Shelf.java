package school.raikes.library.libraryserver.model.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Shelf {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private int number;

  @OneToMany(mappedBy = "location")
  @ToString.Exclude
  private List<Copy> copies;
}
