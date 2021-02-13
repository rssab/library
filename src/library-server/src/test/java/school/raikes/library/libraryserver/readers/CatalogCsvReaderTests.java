package school.raikes.library.libraryserver.readers;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import school.raikes.library.libraryserver.model.entity.*;

/** Tests for the {@link CatalogCsvReader} class. */
public class CatalogCsvReaderTests {
  @Test
  public void testCatalogCsvReaderReadsSampleCatalogCorrectly() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream = this.getClass().getResourceAsStream("/good_catalog.csv");
    reader.read(inputStream);

    Assertions.assertEquals(4, reader.getBooks().size());
    Assertions.assertEquals(5, reader.getAuthors().size());
    Assertions.assertEquals(1, reader.getTags().size());
    Assertions.assertEquals(1, reader.getShelves().size());

    // Completely Check a Book to verify fields correct.
    Optional<Book> karlBookQuery =
        reader.getBooks().stream()
            .filter((b) -> b.getTitle().equals("Karl's Big Happy Adventure"))
            .findFirst();
    Assertions.assertTrue(karlBookQuery.isPresent());
    Book karlBook = karlBookQuery.get();
    Assertions.assertEquals("Karl Moves to the Bay Area", karlBook.getSubtitle());
    Assertions.assertEquals("123456789", karlBook.getIsbn());
    Assertions.assertEquals(1, karlBook.getEdition());
    Assertions.assertEquals(
        new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-14"), karlBook.getPublishDate());

    Assertions.assertEquals(1, karlBook.getCopies().size());
    Copy karlBookCopy = karlBook.getCopies().iterator().next();
    Assertions.assertEquals(karlBook, karlBookCopy.getBook());
    Assertions.assertEquals(
        new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-15"), karlBookCopy.getAcquisitionDate());
    Assertions.assertEquals(1, karlBookCopy.getLocation().getNumber());
    Assertions.assertEquals(1, karlBookCopy.getBarcode());

    Assertions.assertEquals(1, karlBook.getAuthors().size());
    Author karlBookAuthor = karlBook.getAuthors().iterator().next();
    Assertions.assertEquals("Karl", karlBookAuthor.getFirstName());
    Assertions.assertEquals("Shaffer", karlBookAuthor.getLastName());
    Assertions.assertNull(karlBookAuthor.getMiddleName());

    Assertions.assertEquals(1, karlBook.getTags().size());
    Assertions.assertEquals("MANUAL", karlBook.getTags().iterator().next().getName());

    // Check to verify multiple editions are separate books.
    long mattVavBookCount =
        reader.getBooks().stream().filter((b) -> b.getTitle().equals("Matthew Vavricek")).count();
    Assertions.assertEquals(2, mattVavBookCount);

    // Check that multiple last name authors are read correctly.
    Optional<Book> computerScienceBookQuery =
        reader.getBooks().stream()
            .filter((b) -> b.getTitle().equals("Computer Science Education"))
            .findFirst();
    Assertions.assertTrue(computerScienceBookQuery.isPresent());
    Book computerScienceBook = computerScienceBookQuery.get();

    Assertions.assertEquals(3, computerScienceBook.getAuthors().size());
    Assertions.assertTrue(
        computerScienceBook.getAuthors().stream()
            .map(Author::getFirstName)
            .allMatch(Objects::isNull));
    Assertions.assertTrue(
        computerScienceBook.getAuthors().stream()
            .map(Author::getMiddleName)
            .allMatch(Objects::isNull));

    Set<String> expectedLastNames = new HashSet<>();
    expectedLastNames.add("Keck");
    expectedLastNames.add("Cooper");
    expectedLastNames.add("Valentine");
    Assertions.assertEquals(
        expectedLastNames,
        computerScienceBook.getAuthors().stream()
            .map(Author::getLastName)
            .collect(Collectors.toSet()));
  }

  @Test
  public void testReadProperlyReferencesExistingValuesWhileReading() throws Exception {
    InputStream inputStream = this.getClass().getResourceAsStream("/good_catalog.csv");

    Shelf s = new Shelf();
    s.setNumber(1);

    Book b = new Book();
    b.setIsbn("123456789");
    b.setTags(new HashSet<>());

    Copy c = new Copy();
    Set<Copy> copies = new HashSet<>();
    copies.add(c);
    b.setCopies(copies);

    Tag t = new Tag();
    t.setName("MANUAL");

    HashMap<String, Book> bookMap = new HashMap<>();
    HashMap<String, Author> authorMap = new HashMap<>();
    HashMap<Integer, Shelf> shelfMap = new HashMap<>();
    HashMap<String, Tag> tagMap = new HashMap<>();

    bookMap.put("123456789", b);
    shelfMap.put(1, s);
    tagMap.put("MANUAL", t);

    CatalogCsvReader catalogCsvReader =
        CatalogCsvReader.builder()
            .authorMap(authorMap)
            .isbnBookMap(bookMap)
            .shelfMap(shelfMap)
            .tagMap(tagMap)
            .build();

    catalogCsvReader.read(inputStream);

    // Verify that everything else read as expected. Of note, the single manual tag should be reused
    // (no new tag),
    // all authors should be read, and no new shelves should be created. Furthermore, the number of
    // books should not
    // be different from the earlier test.
    Assertions.assertEquals(4, bookMap.keySet().size());
    Assertions.assertEquals(1, tagMap.keySet().size());
    Assertions.assertEquals(5, authorMap.keySet().size());
    Assertions.assertEquals(1, shelfMap.keySet().size());

    // Check to ensure that a new copy was added to the book instead of a new book being created.
    Assertions.assertEquals(bookMap.get("123456789").getCopies().size(), 2);

    // Check to ensure that book was not modifed other than new copy.
    Book expectedBook = new Book();
    expectedBook.setIsbn("123456789");
    expectedBook.setCopies(copies);
    expectedBook.setTags(new HashSet<>());
    Assertions.assertEquals(expectedBook, bookMap.get("123456789"));
  }

  @Test
  public void testReadThrowsExceptionForBadFile() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream = this.getClass().getResourceAsStream("/bad_catalog_not_csv.csv");
    Assertions.assertThrows(IllegalArgumentException.class, () -> reader.read(inputStream));
  }

  @Test
  public void testReadThrowsExceptionForBadBarcode() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream = this.getClass().getResourceAsStream("/bad_catalog_bad_barcode.csv");
    Assertions.assertThrows(ParseException.class, () -> reader.read(inputStream));
  }

  @Test
  public void testReadThrowsExceptionForBadEdition() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream = this.getClass().getResourceAsStream("/bad_catalog_bad_edition.csv");
    Assertions.assertThrows(ParseException.class, () -> reader.read(inputStream));
  }

  @Test
  public void testReadThrowsExceptionForBadPublishDate() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream =
        this.getClass().getResourceAsStream("/bad_catalog_bad_publish_date.csv");
    Assertions.assertThrows(ParseException.class, () -> reader.read(inputStream));
  }

  @Test
  public void testReadThrowsExceptionForBadAcquisitionDate() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream =
        this.getClass().getResourceAsStream("/bad_catalog_bad_acquisition_date.csv");
    Assertions.assertThrows(ParseException.class, () -> reader.read(inputStream));
  }

  @Test
  public void testReadThrowsExceptionForBadShelf() throws Exception {
    CatalogCsvReader reader = CatalogCsvReader.builder().build();
    InputStream inputStream = this.getClass().getResourceAsStream("/bad_catalog_bad_shelf.csv");
    Assertions.assertThrows(ParseException.class, () -> reader.read(inputStream));
  }
}
