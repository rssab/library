package school.raikes.library.libraryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.raikes.library.libraryserver.security.JwtTokenFilterConfigurer;
import school.raikes.library.libraryserver.security.JwtTokenProvider;

/**
 * Global configuration class for security. Handles the use of JWT auth and configures Spring to
 * respect JWT as its central auth scheme. Also configures the pin encryption with BCrypt.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public WebSecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  protected void configure(HttpSecurity http) throws Exception {

    // Disable CSRF (cross site request forgery)
    http.csrf().disable();

    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Entry points
    http.authorizeRequests().anyRequest().permitAll();

    // If a user tries to access a resource without having enough permissions
    http.exceptionHandling().accessDeniedPage("/login");

    // Apply JWT
    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    // Optional. if you want to test the API from a browser.
    // http.httpBasic();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
