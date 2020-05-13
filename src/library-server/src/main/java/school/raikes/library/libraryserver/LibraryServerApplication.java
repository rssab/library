package school.raikes.library.libraryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class LibraryServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(LibraryServerApplication.class, args);
  }
}
