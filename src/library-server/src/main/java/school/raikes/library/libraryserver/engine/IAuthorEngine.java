package school.raikes.library.libraryserver.engine;


import school.raikes.library.libraryserver.model.entity.Author;

/**
 * Engine interface for managing {@link Author}s.
 */
public interface IAuthorEngine {
  Iterable<Author> findAll();

  Author save(Author author);

  void delete(Author author);
}
