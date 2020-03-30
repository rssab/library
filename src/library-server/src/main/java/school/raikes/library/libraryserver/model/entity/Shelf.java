package school.raikes.library.libraryserver.model.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Shelf {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private int number;

  @OneToMany(mappedBy = "location")
  private List<Copy> copies;
}
