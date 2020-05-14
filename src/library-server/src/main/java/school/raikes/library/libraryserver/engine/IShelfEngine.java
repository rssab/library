package school.raikes.library.libraryserver.engine;

import java.util.Map;
import java.util.Optional;
import school.raikes.library.libraryserver.model.entity.Shelf;

/** Engine interface for managing {@link Shelf}s */
public interface IShelfEngine {
  Iterable<Shelf> findAll();

  Optional<Shelf> findByNumber(int number);

  Map<Integer, Shelf> loadNumberShelfMap();

  Shelf save(Shelf shelf);

  Iterable<Shelf> saveAll(Iterable<Shelf> shelves);

  void delete(Shelf shelf);
}
