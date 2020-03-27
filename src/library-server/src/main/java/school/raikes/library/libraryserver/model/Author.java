package school.raikes.library.libraryserver.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String firstName;

  @Column private String lastName;

}
