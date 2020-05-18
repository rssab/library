package school.raikes.library.libraryserver.accessor;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.raikes.library.libraryserver.model.entity.Checkout;
import school.raikes.library.libraryserver.model.entity.Copy;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;

/** Accessor interface for {@link Checkout}s. */
@Repository
public interface ICheckoutAccessor extends CrudRepository<Checkout, Long> {
  Iterable<Checkout> findByCopy(Copy copy);

  Optional<Checkout> findByCopyAndCheckinDateIsNull(Copy copy);

  boolean existsCheckoutByCopyAndCheckinDateIsNull(Copy copy);

  Iterable<Checkout> findByRecipient(LibraryAccount account);

  Iterable<Checkout> findByRecipientNuid(String nuid);

  Iterable<Checkout> findByCheckoutDateBetween(Date start, Date end);

  Iterable<Checkout> findByDueDateBetween(Date start, Date end);

  Iterable<Checkout> findByCheckinDateBetween(Date start, Date end);

  Iterable<Checkout> findByCheckinDateIsNull();
}
