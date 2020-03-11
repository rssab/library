package school.raikes.library.libraryserver.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Type {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 64)
  private String name;
}
