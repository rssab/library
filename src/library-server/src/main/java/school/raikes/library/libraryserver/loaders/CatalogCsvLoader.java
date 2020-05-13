package school.raikes.library.libraryserver.loaders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import school.raikes.library.libraryserver.engine.IAuthorEngine;
import school.raikes.library.libraryserver.engine.IBookEngine;
import school.raikes.library.libraryserver.engine.IShelfEngine;
import school.raikes.library.libraryserver.engine.ITagEngine;
import school.raikes.library.libraryserver.model.entity.Book;
import school.raikes.library.libraryserver.readers.CatalogCsvReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;

@Lazy
@Component
@Slf4j
public class CatalogCsvLoader {

  private final IBookEngine bookEngine;
  private final IAuthorEngine authorEngine;
  private final IShelfEngine shelfEngine;
  private final ITagEngine tagEngine;

  private final CatalogCsvReader catalogCsvReader;

  @Autowired
  public CatalogCsvLoader(IBookEngine bookEngine, IAuthorEngine authorEngine, IShelfEngine shelfEngine, ITagEngine tagEngine) {
    this.bookEngine = bookEngine;
    this.authorEngine = authorEngine;
    this.shelfEngine = shelfEngine;
    this.tagEngine = tagEngine;

    catalogCsvReader = CatalogCsvReader
        .builder()
        .isbnBookMap(this.bookEngine.loadIsbnBookMap())
        .authorMap(this.authorEngine.loadNameAuthorMap())
        .shelfMap(this.shelfEngine.loadNumberShelfMap())
        .tagMap(this.tagEngine.loadNameTagMap())
        .build();
  }

  public void load(File file) throws IOException, ParseException {
    load(new FileInputStream(file));
  }

  public void load(InputStream inputStream) throws IOException, ParseException {
    this.catalogCsvReader.read(inputStream);

    this.tagEngine.saveAll(this.catalogCsvReader.getTags());
    this.authorEngine.saveAll(this.catalogCsvReader.getAuthors());
    this.shelfEngine.saveAll(this.catalogCsvReader.getShelves());
    this.bookEngine.saveAll(this.catalogCsvReader.getBooks());
  }

}
