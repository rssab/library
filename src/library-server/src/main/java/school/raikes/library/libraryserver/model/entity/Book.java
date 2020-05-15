package school.raikes.library.libraryserver.model.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToMany List<Tag> tags;

  @Column(length = 64)
  private String isbn;

  @ManyToOne private Type type;

  @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
  private List<Copy> copies;

  @Column private String title;

  @Column private String subtitle;

  @Column private int edition;

  @Column @Lob private byte[] coverImage;

  @Column private String coverImageMimeType;

  @ManyToMany private List<Author> authors;

  @Temporal(TemporalType.DATE)
  private Date publishDate;
}
