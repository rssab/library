package school.raikes.library.libraryserver.loaders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Lazy
@Component
@Slf4j
public class CatalogCsvLoader {

  // private final CatalogCsvReader catalogCsvReader;

  @Autowired
  public CatalogCsvLoader() {

  }

  public void load(File file) throws IOException {

  }

  public void load(InputStream inputStream) throws IOException {

  }

}
