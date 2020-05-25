package school.raikes.library.libraryserver.accessor;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Book;

/** Accessor interface for {@link Book}s */
@Repository
public interface IBookAccessor extends CrudRepository<Book, Long> {
  Optional<Book> findByIsbn(String isbn);
}
