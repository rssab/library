package school.raikes.library.libraryserver.accessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Tag;

import java.util.Optional;

/**
 * Accessor interface for {@link Tag}s
 */
@Repository
public interface ITagAccessor extends CrudRepository<Tag, Long> {
  Optional<Tag> findByName(String name);
}
