package school.raikes.library.libraryserver.engine;

import java.util.Map;
import java.util.Optional;
import school.raikes.library.libraryserver.model.entity.Book;
import school.raikes.library.libraryserver.model.entity.Copy;

/** Engine interface for managing {@link Book}s. */
public interface IBookEngine {
  Iterable<Book> findAll();

  Optional<Book> findByIsbn(String isbn);

  Book findByCopy(Copy copy);

  /**
   * Retrieves all books in a {@link Map} of the book's ISBN to the individual {@link Book} entity.
   * This is primarily used in the the reading process where serialized data is imported into the
   * system.
   */
  Map<String, Book> getIsbnBookMap();

  Book save(Book book);

  Iterable<Book> saveAll(Iterable<Book> books);

  void delete(Book book);
}
