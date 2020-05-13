package school.raikes.library.libraryserver.engine;


import school.raikes.library.libraryserver.model.entity.Author;

import java.util.Map;

/**
 * Engine interface for managing {@link Author}s.
 */
public interface IAuthorEngine {
  Iterable<Author> findAll();

  Map<String, Author> loadNameAuthorMap();

  Author save(Author author);

  Iterable<Author> saveAll(Iterable<Author> authors);

  void delete(Author author);
}
