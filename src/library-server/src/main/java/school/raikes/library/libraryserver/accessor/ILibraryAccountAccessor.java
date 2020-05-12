package school.raikes.library.libraryserver.accessor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;
import school.raikes.library.libraryserver.model.entity.Role;

/**
 * Accessor interface for {@link LibraryAccount}s.
 */
@Repository
public interface ILibraryAccountAccessor extends CrudRepository<LibraryAccount, Long> {
  LibraryAccount findByNuid(String nuid);

  Iterable<LibraryAccount> findAllByRole(Role role);

  boolean existsByNuid(String nuid);
}
