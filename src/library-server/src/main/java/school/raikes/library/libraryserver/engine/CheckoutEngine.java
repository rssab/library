package school.raikes.library.libraryserver.engine;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.ICheckoutAccessor;
import school.raikes.library.libraryserver.accessor.ICopyAccessor;
import school.raikes.library.libraryserver.accessor.ILibraryAccountAccessor;
import school.raikes.library.libraryserver.exceptions.WebApplicationException;
import school.raikes.library.libraryserver.model.entity.Checkout;
import school.raikes.library.libraryserver.model.entity.Copy;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;

@Service
public class CheckoutEngine implements ICheckoutEngine {
  /** Denotes the amount of time a book is checked out by default. */
  private static final int CHECKOUT_DURATION_MILLIS = 1209600000; // 2 Weeks

  /** Denotes the amount of time a checkout may be extended. */
  private static final int EXTENSION_DURATION_MILLIS = 604800000; // 1 Week.

  /** Denotes the amount of time before a book is due that it may be extended. */
  private static final int EXTENSION_WINDOW = 86400000; // 24 Hours

  private final ICheckoutAccessor checkoutAccessor;
  private final ILibraryAccountAccessor libraryAccountAccessor;
  private final ICopyAccessor copyAccessor;

  @Autowired
  public CheckoutEngine(
      ICheckoutAccessor checkoutAccessor,
      ILibraryAccountAccessor libraryAccountAccessor,
      ICopyAccessor copyAccessor) {
    this.checkoutAccessor = checkoutAccessor;
    this.libraryAccountAccessor = libraryAccountAccessor;
    this.copyAccessor = copyAccessor;
  }

  @Override
  public Iterable<Checkout> findAll() {
    return this.checkoutAccessor.findAll();
  }

  @Override
  public Optional<Checkout> findById(Long id) {
    return this.checkoutAccessor.findById(id);
  }

  @Override
  public Iterable<Checkout> findByCopy(Copy copy) {
    return this.checkoutAccessor.findByCopy(copy);
  }

  @Override
  public Optional<Checkout> findCurrentCheckout(Copy copy) {
    return this.checkoutAccessor.findByCopyAndCheckinDateIsNull(copy);
  }

  @Override
  public boolean isCheckedOut(Copy copy) {
    return this.checkoutAccessor.existsCheckoutByCopyAndCheckinDateIsNull(copy);
  }

  @Override
  public Iterable<Checkout> findByRecipient(LibraryAccount libraryAccount) {
    return this.checkoutAccessor.findByRecipient(libraryAccount);
  }

  @Override
  public Iterable<Checkout> findByRecipient(String nuid) {
    return this.checkoutAccessor.findByRecipientNuid(nuid);
  }

  @Override
  public Iterable<Checkout> findByCheckoutDateBetween(Date start, Date end) {
    return this.checkoutAccessor.findByCheckoutDateBetween(start, end);
  }

  @Override
  public Iterable<Checkout> findByDueDateBetween(Date start, Date end) {
    return this.checkoutAccessor.findByDueDateBetween(start, end);
  }

  @Override
  public Iterable<Checkout> findByCheckinDateBetween(Date start, Date end) {
    return this.checkoutAccessor.findByCheckinDateBetween(start, end);
  }

  @Override
  public Iterable<Checkout> findCheckedOut() {
    return this.checkoutAccessor.findByCheckinDateIsNull();
  }

  @Override
  public Checkout checkout(LibraryAccount libraryAccount, Copy copy) {
    Checkout checkout = new Checkout();

    checkout.setRecipient(libraryAccount);
    checkout.setCopy(copy);

    Calendar calendar = Calendar.getInstance();
    checkout.setCheckoutDate(calendar.getTime());
    calendar.add(Calendar.MILLISECOND, CHECKOUT_DURATION_MILLIS);
    checkout.setDueDate(calendar.getTime());

    return this.checkoutAccessor.save(checkout);
  }

  @Override
  public Checkout checkout(String nuid, Copy copy) {
    Optional<LibraryAccount> account = this.libraryAccountAccessor.findByNuid(nuid);

    if (!account.isPresent()) {
      throw new WebApplicationException(
          String.format("No user exists with NUID: %s", nuid), HttpStatus.NOT_FOUND);
    }

    return checkout(account.get(), copy);
  }

  @Override
  public Checkout checkout(String nuid, String barcode) {
    Optional<Copy> copy = copyAccessor.findByBarcode(barcode);

    if (!copy.isPresent()) {
      throw new WebApplicationException(
          String.format("The barcode (%s) does not correspond to a known book", barcode),
          HttpStatus.NOT_FOUND);
    }

    Optional<LibraryAccount> account = this.libraryAccountAccessor.findByNuid(nuid);

    if (!account.isPresent()) {
      throw new WebApplicationException(
          String.format("No user exists with NUID: %s", nuid), HttpStatus.NOT_FOUND);
    }

    return checkout(account.get(), copy.get());
  }

  @Override
  public Checkout checkin(Checkout checkout) {
    checkout.setCheckinDate(Calendar.getInstance().getTime());
    return save(checkout);
  }

  @Override
  public Checkout save(Checkout checkout) {
    return this.checkoutAccessor.save(checkout);
  }

  @Override
  public Iterable<Checkout> saveAll(Iterable<Checkout> checkouts) {
    return this.checkoutAccessor.saveAll(checkouts);
  }

  @Override
  public void delete(Checkout checkout) {
    this.checkoutAccessor.delete(checkout);
  }

  @Override
  public void deleteAll(Iterable<Checkout> checkouts) {
    this.checkoutAccessor.deleteAll(checkouts);
  }
}
