package school.raikes.library.libraryserver.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
