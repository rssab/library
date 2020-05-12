package school.raikes.library.libraryserver.engine;

import school.raikes.library.libraryserver.model.entity.Shelf;

import java.util.Optional;

/**
 * Engine interface for managing {@link Shelf}s
 */
public interface IShelfEngine {
  Iterable<Shelf> findAll();

  Optional<Shelf> findByNumber(int number);

  Shelf save(Shelf shelf);

  Iterable<Shelf> saveAll(Iterable<Shelf> shelves);

  void delete(Shelf shelf);
}
