package school.raikes.library.libraryserver.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Book {
  @ManyToMany
  List<Tag> tags;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 64)
  private String isbn;

  @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Copy> copies;

  @Column
  private String title;

  @Column
  private String subtitle;

  @Column
  private int edition;

  @Column
  @Lob
  private byte[] coverImage;

  @Column
  private String coverImageMimeType;

  @ManyToMany
  private List<Author> authors;

  @Temporal(TemporalType.DATE)
  private Date publishDate;
}
