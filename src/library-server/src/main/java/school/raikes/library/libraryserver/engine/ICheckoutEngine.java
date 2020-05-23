package school.raikes.library.libraryserver.engine;

import java.util.Date;
import java.util.Optional;
import school.raikes.library.libraryserver.model.entity.Checkout;
import school.raikes.library.libraryserver.model.entity.Copy;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;

/** Engine interface for managing {@link Checkout}s. */
public interface ICheckoutEngine {
  Iterable<Checkout> findAll();

  Optional<Checkout> findById(Long id);

  Iterable<Checkout> findByCopy(Copy copy);

  Optional<Checkout> findCurrentCheckout(Copy copy);

  boolean isCheckedOut(Copy copy);

  Iterable<Checkout> findByRecipient(LibraryAccount libraryAccount);

  Iterable<Checkout> findByRecipient(String nuid);

  Iterable<Checkout> findByCheckoutDateBetween(Date start, Date end);

  Iterable<Checkout> findByDueDateBetween(Date start, Date end);

  Iterable<Checkout> findByCheckinDateBetween(Date start, Date end);

  Iterable<Checkout> findCheckedOut();

  Checkout checkout(LibraryAccount libraryAccount, Copy copy);

  Checkout checkout(String nuid, Copy copy);

  Checkout checkout(String nuid, String barcode);

  Checkout checkin(Checkout checkout);

  Checkout save(Checkout checkout);

  Iterable<Checkout> saveAll(Iterable<Checkout> checkouts);

  void delete(Checkout checkout);

  void deleteAll(Iterable<Checkout> checkouts);
}
