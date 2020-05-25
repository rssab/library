package school.raikes.library.libraryserver.accessor;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Tag;

/** Accessor interface for {@link Tag}s */
@Repository
public interface ITagAccessor extends CrudRepository<Tag, Long> {
  Optional<Tag> findByName(String name);
}
