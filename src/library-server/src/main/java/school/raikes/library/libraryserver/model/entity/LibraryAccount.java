package school.raikes.library.libraryserver.model.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
public class LibraryAccount implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, length = 8, nullable = false)
  @Size(min = 8, max = 8)
  private String nuid;

  @Column(length = 60, nullable = false)
  private String pin;

  @ManyToOne(optional = false)
  private Role role;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();

    authorities.add(new SimpleGrantedAuthority(role.getName()));

    return authorities;
  }

  @Override
  public String getPassword() {
    return pin;
  }

  @Override
  public String getUsername() {
    return nuid;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
