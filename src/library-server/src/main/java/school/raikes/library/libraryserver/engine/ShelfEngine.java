package school.raikes.library.libraryserver.engine;

import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.IShelfAccessor;
import school.raikes.library.libraryserver.model.entity.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Basic implementation of the {@link IShelfEngine} interface.
 */
@Service
public class ShelfEngine implements IShelfEngine {
  private final IShelfAccessor shelfAccessor;

  public ShelfEngine(IShelfAccessor shelfAccessor) {
    this.shelfAccessor = shelfAccessor;
  }

  @Override
  public Iterable<Shelf> findAll() {
    return this.shelfAccessor.findAll();
  }

  @Override
  public Optional<Shelf> findByNumber(int number) {
    return this.shelfAccessor.findByNumber(number);
  }

  @Override
  public Map<Integer, Shelf> loadNumberShelfMap() {
    Map<Integer, Shelf> shelfMap = new HashMap<>();

    findAll().forEach((s) -> shelfMap.put(s.getNumber(), s));

    return shelfMap;
  }

  @Override
  public Shelf save(Shelf shelf) {
    return this.shelfAccessor.save(shelf);
  }

  @Override
  public Iterable<Shelf> saveAll(Iterable<Shelf> shelves) {
    return this.shelfAccessor.saveAll(shelves);
  }

  @Override
  public void delete(Shelf shelf) {
    this.shelfAccessor.delete(shelf);
  }
}
