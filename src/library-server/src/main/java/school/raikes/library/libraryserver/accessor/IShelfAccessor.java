package school.raikes.library.libraryserver.accessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Shelf;

import java.util.Optional;

/**
 * Accessor interface for {@link Shelf}s
 */
@Repository
public interface IShelfAccessor extends CrudRepository<Shelf, Long> {
  Optional<Shelf> findByNumber(int number);
}
