package school.raikes.library.libraryserver.accessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Author;

/** Accessor interface for {@link Author}s */
@Repository
public interface IAuthorAccessor extends CrudRepository<Author, Long> {}
