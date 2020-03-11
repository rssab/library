package school.raikes.library.libraryserver.accessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Role;

/** Accessor interface for {@link Role}s. */
@Repository
public interface IRoleAccessor extends CrudRepository<Role, Long> {
  Role findByName(String name);
}
