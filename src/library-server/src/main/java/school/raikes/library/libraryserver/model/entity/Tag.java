package school.raikes.library.libraryserver.model.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;
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
  @ToString.Exclude
  private List<Book> books;
}
