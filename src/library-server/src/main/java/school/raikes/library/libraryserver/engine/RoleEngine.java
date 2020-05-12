package school.raikes.library.libraryserver.engine;

import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.IRoleAccessor;
import school.raikes.library.libraryserver.model.entity.Role;

/**
 * Basic implementation of the {@link IRoleEngine}.
 */
@Service
public class RoleEngine implements IRoleEngine {

  private final IRoleAccessor roleAccessor;

  public RoleEngine(IRoleAccessor roleAccessor) {
    this.roleAccessor = roleAccessor;
  }

  @Override
  public Iterable<Role> findAll() {
    return roleAccessor.findAll();
  }

  @Override
  public Role findByName(String name) {
    return roleAccessor.findByName(name);
  }
}
