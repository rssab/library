package school.raikes.library.libraryserver.engine;

import java.util.Map;
import school.raikes.library.libraryserver.model.entity.Author;

/** Engine interface for managing {@link Author}s. */
public interface IAuthorEngine {
  Iterable<Author> findAll();

  /**
   * Retrieves all authors in a {@link Map} of the author's first, middle, and last name fields
   * (first + middle + last, no spaces) to the individual author entity. This is primarily used in
   * the reading process where serialized data is imported into the system.
   */
  Map<String, Author> getNameAuthorMap();

  Author save(Author author);

  Iterable<Author> saveAll(Iterable<Author> authors);

  void delete(Author author);
}
