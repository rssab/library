package school.raikes.library.libraryserver.model.entity;

import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "tags")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<Book> books;
}
