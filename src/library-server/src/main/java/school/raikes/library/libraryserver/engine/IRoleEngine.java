package school.raikes.library.libraryserver.engine;

import school.raikes.library.libraryserver.model.entity.Role;

/**
 * Engine interface for managing roles. Only allows for querying for roles in a simple manner, since
 * roles should be added to the database before the application is even started.
 */
public interface IRoleEngine {
  Iterable<Role> findAll();

  Role findByName(String name);
}
