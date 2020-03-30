package school.raikes.library.libraryserver.model.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @ManyToMany(mappedBy = "tags")
  private List<Book> books;
}
