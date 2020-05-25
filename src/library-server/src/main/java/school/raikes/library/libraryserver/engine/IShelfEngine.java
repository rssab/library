package school.raikes.library.libraryserver.engine;

import java.util.Map;
import java.util.Optional;
import school.raikes.library.libraryserver.model.entity.Shelf;

/** Engine interface for managing {@link Shelf}s */
public interface IShelfEngine {
  Iterable<Shelf> findAll();

  Optional<Shelf> findByNumber(int number);

  /**
   * Retrieves all shelves in a {@link Map} of the Shelf's number to the individual shelf entity.
   * This is primarily used in the reading process where serialized data is imported into the
   * system.
   */
  Map<Integer, Shelf> getNumberShelfMap();

  Shelf save(Shelf shelf);

  Iterable<Shelf> saveAll(Iterable<Shelf> shelves);

  void delete(Shelf shelf);
}
