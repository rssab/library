package school.raikes.library.libraryserver.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Type {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 64)
  private String name;

}
