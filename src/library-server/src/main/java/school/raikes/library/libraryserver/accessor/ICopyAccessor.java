package school.raikes.library.libraryserver.accessor;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Book;
import school.raikes.library.libraryserver.model.entity.Copy;

/** Accessor interface for {@link Copy}s */
@Repository
public interface ICopyAccessor extends CrudRepository<Copy, Long> {
  Optional<Copy> findByBarcode(long barcode);

  Iterable<Copy> findAllByBook(Book book);

  Iterable<Copy> findAllByBookIsbn(String isbn);
}
