package school.raikes.library.libraryserver.security.enums;

/** Enumeration of the system's configured roles. */
public enum Role {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER"),
  UNKNOWN("ROLE_UNKNOWN");

  /**
   * The Spring Security name that reflects the name of the role as should be stored in the system's
   * database
   */
  private final String name;

  /**
   * The corresponding authority granted to holders of this role. This is based on the current
   * configuration of the User class which assigns authorities based on role.
   */
  private final String authority;

  Role(String name) {
    this.name = name;
    this.authority = this.name.substring(5);
  }

  public String getName() {
    return name;
  }

  public String getAuthority() {
    return authority;
  }
}
