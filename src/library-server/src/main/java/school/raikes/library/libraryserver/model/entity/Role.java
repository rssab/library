package school.raikes.library.libraryserver.model.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 32)
  private String name;
}
