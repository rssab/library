package school.raikes.library.libraryserver.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.ICopyAccessor;
import school.raikes.library.libraryserver.model.entity.Copy;

/**
 * Basic implementation of the {@link ICopyEngine} interface.
 */
@Service
public class CopyEngine implements ICopyEngine {
  private final ICopyAccessor copyAccessor;

  @Autowired
  public CopyEngine(ICopyAccessor copyAccessor) {
    this.copyAccessor = copyAccessor;
  }

  @Override
  public Iterable<Copy> findAll() {
    return this.copyAccessor.findAll();
  }

  @Override
  public Iterable<Copy> findAllByBookIsbn(String isbn) {
    return this.copyAccessor.findAllByBookIsbn(isbn);
  }

  @Override
  public Copy save(Copy copy) {
    return this.copyAccessor.save(copy);
  }

  @Override
  public Iterable<Copy> saveAll(Iterable<Copy> copies) {
    return this.copyAccessor.saveAll(copies);
  }

  @Override
  public void delete(Copy copy) {
    this.copyAccessor.delete(copy);
  }
}
