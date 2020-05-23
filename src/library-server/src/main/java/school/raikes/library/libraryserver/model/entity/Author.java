package school.raikes.library.libraryserver.model.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(
    uniqueConstraints = {
      // Authors should have unique names.
      @UniqueConstraint(columnNames = {"firstName", "middleName", "lastName"})
    })
@Data
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String firstName;

  @Column private String middleName;

  @Column(nullable = false)
  private String lastName;
}
