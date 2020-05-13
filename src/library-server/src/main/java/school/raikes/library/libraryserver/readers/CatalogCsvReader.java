package school.raikes.library.libraryserver.readers;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.tupilabs.human_name_parser.HumanNameParserParser;
import lombok.Builder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import school.raikes.library.libraryserver.model.entity.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Builder
public class CatalogCsvReader {

  public static final int LONG_NAME_DETERMINANT = 6;
  public static final Set<String> EMPTY_VALUES = ImmutableSet.of("", "-", "undefined");
  public static final String DATE_FORMAT = "MM/dd/yyyy";
  public static final String MANUAL_TAG_NAME = "MANUAL";

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
   * Reads a Catalog from a CSV input.
   *
   * @param inputStream An {@link InputStream} for a CSV file.
   * @throws IOException if any errors occur while parsing the CSV.
   */
  public CatalogCsvReader read(InputStream inputStream) throws IOException, ParseException {
    CSVParser parser = CSVParser.parse(inputStream, Charsets.UTF_8, CSVFormat.EXCEL.withFirstRecordAsHeader());

    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    for (CSVRecord row : parser) {
      String barcode = row.get(BARCODE_KEY).trim();
      String isbn = row.get(ISBN_KEY).trim();
      String title = row.get(TITLE_KEY).trim();
      String subtitle = row.get(SUBTITLE_KEY).trim();
      String edition = row.get(EDITION_KEY).trim();
      String authors = row.get(AUTHORS_KEY).trim();
      String publishDate = row.get(PUBLISH_DATE_KEY).trim();
      String acquisitionDate = row.get(ACQUISITION_DATE_KEY).trim();
      String shelf = row.get(SHELF_KEY).trim();
      String manual = row.get(MANUAL_KEY).trim();

      Long parsedBarcode;
      try {
        // Assume Code 3 of 9 barcode, trim check digit and parse into value.
        parsedBarcode = Long.parseLong(barcode.substring(0, barcode.length() - 1));
      } catch (NumberFormatException nfe) {
        // Safe cast as we generally wouldn't expect to be reading a CSV file over 2B records.
        throw new ParseException("Failed to parse Barcode", (int) parser.getCurrentLineNumber());
      }

      String parsedIsbn;
      if (!EMPTY_VALUES.contains(isbn)) {
        parsedIsbn = isbn;
      } else {
        parsedIsbn = null;
      }

      String parsedSubtitle;
      if (!EMPTY_VALUES.contains(subtitle)) {
        parsedSubtitle = subtitle;
      } else {
        parsedSubtitle = null;
      }

      Integer parsedEdition;
      if (!EMPTY_VALUES.contains(edition)) {
        try {
          parsedEdition = Integer.parseInt(edition);
        } catch (NumberFormatException nfe) {
          throw new ParseException("Failed to parse Edition", (int) parser.getCurrentLineNumber());
        }
      } else {
        parsedEdition = null;
      }

      List<String[]> parsedAuthors = new LinkedList<>();
      try {
        // Split in case of multiple authors and attempt to parse each name individually.
        for (String author : Splitter.on(",").trimResults().split(authors)) {

          // If just last names or organization (long) names just add and continue.
          long nameLength = Splitter.on(CharMatcher.whitespace()).splitToStream(author).count();
          if (nameLength == 1 || nameLength > LONG_NAME_DETERMINANT) {
            parsedAuthors.add(new String[]{null,null,author});
            continue;
          }

          HumanNameParserParser nameParser = new HumanNameParserParser(author.trim());
          parsedAuthors.add(new String[]{
              nameParser.getFirst().isEmpty() ? null : nameParser.getFirst(),
              nameParser.getMiddle().isEmpty() ? null : nameParser.getMiddle(),
              nameParser.getLast().isEmpty() ? null : nameParser.getLast()});
        }
      } catch (com.tupilabs.human_name_parser.ParseException pe) {
        // If unable to parse, assume organization and set full author string to last name.
        parsedAuthors.add(new String[]{null,null,authors});
      }

      Date parsedPublishDate;
      if (!EMPTY_VALUES.contains(publishDate)) {
        try {
          parsedPublishDate = dateFormat.parse(publishDate);
        } catch (ParseException pe) {
          throw new ParseException("Failed to parse Publish Date", (int) parser.getCurrentLineNumber());
        }
      } else {
        parsedPublishDate = null;
      }

      Date parsedAcquisitionDate;
      if (!EMPTY_VALUES.contains(acquisitionDate)) {
        try {
          parsedAcquisitionDate = dateFormat.parse(acquisitionDate);
        } catch (ParseException pe) {
          throw new ParseException("Failed to parse Acquisition Date", (int) parser.getCurrentLineNumber());
        }
      } else {
        parsedAcquisitionDate = null;
      }

      Integer parsedShelf;
      if (!EMPTY_VALUES.contains(shelf)) {
        try {
          parsedShelf = Integer.parseInt(shelf);
        } catch (NumberFormatException nfe) {
          throw new ParseException("Failed to parse Shelf", (int) parser.getCurrentLineNumber());
        }
      } else {
        parsedShelf = null;
      }

      Boolean parsedManual;
      if (!EMPTY_VALUES.contains(manual)) {
        parsedManual = Boolean.parseBoolean(manual);
      } else {
        parsedManual = false;
      }

      // Using parsed values, create or update the requisite entities
      List<Author> authorEntities = new ArrayList<>();
      for (String[] author : parsedAuthors) {
        String authorKey = String.join("",author);
        Author a = authorMap.get(authorKey);
        if (a == null) {
          a = new Author();
          a.setFirstName(author[0]);
          a.setMiddleName(author[1]);
          a.setLastName(author[2]);
          authorMap.put(authorKey, a);
        }
        authorEntities.add(a);
      }

      Shelf s = shelfMap.get(parsedShelf);
      if (s == null) {
        s = new Shelf();
        s.setNumber(parsedShelf);
        shelfMap.put(parsedShelf, s);
      }

      Copy c = new Copy();
      c.setBarcode(parsedBarcode);
      c.setAcquisitionDate(parsedAcquisitionDate);
      c.setLocation(s);

      Tag manualTag = tagMap.get(MANUAL_TAG_NAME);
      if (manualTag == null) {
        manualTag = new Tag();
        manualTag.setName(MANUAL_TAG_NAME);
        tagMap.put(MANUAL_TAG_NAME, manualTag);
      }

      Book b = isbnBookMap.get(isbn);
      if (b == null) {
        b = new Book();

        b.setIsbn(parsedIsbn);
        b.setTitle(title);
        b.setSubtitle(parsedSubtitle);
        b.setEdition(parsedEdition);
        b.setCopies(new ArrayList<>());
        b.setTags(new ArrayList<>());
        b.setAuthors(authorEntities);
        b.setPublishDate(parsedPublishDate);

        // If the entry does not have an ISBN put it in the map with a UUID so it is still stored in the map.
        isbnBookMap.put(parsedIsbn == null ? UUID.randomUUID().toString() : isbn, b);
      }

      c.setBook(b);
      b.getCopies().add(c);

      if (parsedManual) {
        b.getTags().add(manualTag);
      }
    }

    return this;
  }

  public Collection<Book> getBooks() {
    return isbnBookMap.values();
  }

  public Collection<Author> getAuthors() {
    return authorMap.values();
  }

  public Collection<Shelf> getShelves() { return shelfMap.values(); }

  public Collection<Tag> getTags() {
    return tagMap.values();
  }

}