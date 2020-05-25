package school.raikes.library.libraryserver.model.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"isbn"})})
@Data
public class Book {
  @ManyToMany List<Tag> tags;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 64)
  private String isbn;

  @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Copy> copies;

  @Column(nullable = false)
  private String title;

  @Column private String subtitle;

  @Column private Integer edition;

  @Column @Lob private byte[] coverImage;

  @Column private String coverImageMimeType;

  @ManyToMany private List<Author> authors;

  @Temporal(TemporalType.DATE)
  private Date publishDate;
}
