package school.raikes.library.libraryserver.engine;

import school.raikes.library.libraryserver.model.entity.Copy;

/**
 * Engine interface for managing {@link Copy}s
 */
public interface ICopyEngine {
  Iterable<Copy> findAll();

  Iterable<Copy> findAllByBookIsbn(String isbn);

  Copy save(Copy copy);

  Iterable<Copy> saveAll(Iterable<Copy> copies);

  void delete(Copy copy);
}
