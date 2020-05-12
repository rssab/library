package school.raikes.library.libraryserver.readers;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import school.raikes.library.libraryserver.model.entity.Author;
import school.raikes.library.libraryserver.model.entity.Book;
import school.raikes.library.libraryserver.model.entity.Shelf;
import school.raikes.library.libraryserver.model.entity.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Builder
public class CatalogCsvReader {

  public static final Set<String> EMPTY_VALUES = ImmutableSet.of("", "-", "undefined");

  /* KEYS */
  public static final String BARCODE_KEY = "Barcode";
  public static final String ISBN_KEY = "ISBN";
  public static final String TITLE_KEY = "Title";
  public static final String SUBTITLE_KEY = "Subtitle";
  public static final String EDITION_KEY = "Edition";
  public static final String AUTHORS_KEY = "Authors";
  public static final String PUBLISH_DATE_KEY = "Publish Date";
  public static final String ACQUISITION_DATE_KEY = "Acquisition Date";
  public static final String SHELF_KEY = "Shelf";
  public static final String MANUAL_KEY = "Manual";

  /* Maps used to track existing items so that new copies are properly linked to their respective book, author, etc */

  @Builder.Default
  private final Map<Integer, Shelf> shelfMap = new HashMap<>();

  @Builder.Default
  private final Map<String, Tag> tagMap = new HashMap<>();

  @Builder.Default
  private final Map<String, Author> authorMap = new HashMap<>();

  @Builder.Default
  private final Map<String, Book> isbnBookMap = new HashMap<>();

  /**
   * Reads a Catalog from a CSV file
   *
   * @param file the CSV file
   * @throws IOException if any errors occur while reading the file or parsing the CSV.
   */
  public void read(File file) throws IOException {
    read(new FileInputStream(file));
  }

  /**
   * Reads a Catalog from a CSV input.
   *
   * @param inputStream An {@link InputStream} for a CSV file.
   * @throws IOException if any errors occur while parsing the CSV.
   */
  public void read(InputStream inputStream) throws IOException {
    CSVParser parser = CSVParser.parse(inputStream, Charsets.UTF_8, CSVFormat.EXCEL.withFirstRecordAsHeader());

    for (CSVRecord row : parser) {

    }
  }

}
