package school.raikes.library.libraryserver.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String firstName;

  @Column private String lastName;

}
