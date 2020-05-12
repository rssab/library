package school.raikes.library.libraryserver.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 32)
  private String name;
}
