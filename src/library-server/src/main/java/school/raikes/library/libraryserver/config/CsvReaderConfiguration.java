package school.raikes.library.libraryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.raikes.library.libraryserver.engine.IAuthorEngine;
import school.raikes.library.libraryserver.engine.IBookEngine;
import school.raikes.library.libraryserver.engine.IShelfEngine;
import school.raikes.library.libraryserver.engine.ITagEngine;
import school.raikes.library.libraryserver.readers.CatalogCsvReader;

@Configuration
public class CsvReaderConfiguration {

  private IBookEngine bookEngine;
  private IAuthorEngine authorEngine;
  private IShelfEngine shelfEngine;
  private ITagEngine tagEngine;

  @Autowired
  public CsvReaderConfiguration(
      IBookEngine bookEngine,
      IAuthorEngine authorEngine,
      IShelfEngine shelfEngine,
      ITagEngine tagEngine) {
    this.bookEngine = bookEngine;
    this.authorEngine = authorEngine;
    this.shelfEngine = shelfEngine;
    this.tagEngine = tagEngine;
  }

  @Bean
  public CatalogCsvReader catalogCsvReader() {
    return CatalogCsvReader.builder()
        .isbnBookMap(this.bookEngine.loadIsbnBookMap())
        .authorMap(this.authorEngine.loadNameAuthorMap())
        .shelfMap(this.shelfEngine.loadNumberShelfMap())
        .tagMap(this.tagEngine.loadNameTagMap())
        .build();
  }
}
