package school.raikes.library.libraryserver.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

  @Column(unique = true, length = 3)
  @Size(min = 8, max = 8)
  private String nuid;

  @Column(length = 60)
  private String pin;

  @ManyToOne @NotNull private Role role;

  @Column private String firstName;

  @Column private String lastName;

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
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
