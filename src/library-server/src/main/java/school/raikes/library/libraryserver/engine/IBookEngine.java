package school.raikes.library.libraryserver.engine;

import school.raikes.library.libraryserver.model.entity.Book;

import java.util.Map;
import java.util.Optional;

/**
 * Engine interface for managing {@link Book}s.
 */
public interface IBookEngine {
  Iterable<Book> findAll();

  Optional<Book> findByIsbn(String isbn);

  Map<String, Book> loadIsbnBookMap();

  Book save(Book book);

  Iterable<Book> saveAll(Iterable<Book> books);

  void delete(Book book);
}
