package school.raikes.library.libraryserver.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.IBookAccessor;
import school.raikes.library.libraryserver.model.entity.Book;

/** Basic implementation of the {@link IBookEngine} interface. */
@Service
public class BookEngine implements IBookEngine {
  private final IBookAccessor bookAccessor;

  public BookEngine(IBookAccessor bookAccessor) {
    this.bookAccessor = bookAccessor;
  }

  @Override
  public Iterable<Book> findAll() {
    return this.bookAccessor.findAll();
  }

  @Override
  public Optional<Book> findByIsbn(String isbn) {
    return this.bookAccessor.findByIsbn(isbn);
  }

  @Override
  public Map<String, Book> loadIsbnBookMap() {
    Map<String, Book> bookMap = new HashMap<>();

    findAll().forEach((b) -> bookMap.put(b.getIsbn(), b));

    return bookMap;
  }

  @Override
  public Book save(Book book) {
    return bookAccessor.save(book);
  }

  @Override
  public Iterable<Book> saveAll(Iterable<Book> books) {
    return bookAccessor.saveAll(books);
  }

  @Override
  public void delete(Book book) {
    bookAccessor.delete(book);
  }
}
