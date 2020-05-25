package school.raikes.library.libraryserver.loaders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import school.raikes.library.libraryserver.engine.IAuthorEngine;
import school.raikes.library.libraryserver.engine.IBookEngine;
import school.raikes.library.libraryserver.engine.IShelfEngine;
import school.raikes.library.libraryserver.engine.ITagEngine;
import school.raikes.library.libraryserver.readers.CatalogCsvReader;

/** Tests for the {@link CatalogCsvLoader} class. */
public class CatalogCsvLoaderTests {
  @Mock IBookEngine bookEngine;

  @Mock IAuthorEngine authorEngine;

  @Mock IShelfEngine shelfEngine;

  @Mock ITagEngine tagEngine;

  @Mock CatalogCsvReader catalogCsvReader;

  @InjectMocks CatalogCsvLoader catalogCsvLoader;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /** Tests for {@link CatalogCsvLoader#load(File)} */
  @Test
  public void testLoadWithFilePassesInputStreamToCatalogCsvReader() throws Exception {
    String testFileContents = "Test File, Please Ignore.";

    File testFile = File.createTempFile("test", null);
    testFile.deleteOnExit();
    try (FileWriter writer = new FileWriter(testFile)) {
      writer.write(testFileContents);
    }

    catalogCsvLoader.load(testFile);

    ArgumentCaptor<InputStream> argumentCaptor = ArgumentCaptor.forClass(InputStream.class);
    verify(catalogCsvReader).read(argumentCaptor.capture());
    byte[] result = ByteStreams.toByteArray(argumentCaptor.getValue());
    Assertions.assertArrayEquals(testFileContents.getBytes(), result);
  }

  @Test
  public void testLoadWithFileSavesResultFromReaderAfterRead() throws Exception {
    String testFileContents = "Test File, Please Ignore.";

    File testFile = File.createTempFile("test", null);
    testFile.deleteOnExit();
    try (FileWriter writer = new FileWriter(testFile)) {
      writer.write(testFileContents);
    }

    catalogCsvLoader.load(testFile);

    verify(this.tagEngine).saveAll(this.catalogCsvReader.getTags());
    verify(this.authorEngine).saveAll(this.catalogCsvReader.getAuthors());
    verify(this.shelfEngine).saveAll(this.catalogCsvReader.getShelves());
    verify(this.bookEngine).saveAll(this.catalogCsvReader.getBooks());
  }

  @Test
  public void testLoadWithFileThrowsExceptionsThrownByCsvReader() throws Exception {
    String testFileContents = "Test File, Please Ignore.";

    File testFile = File.createTempFile("test", null);
    testFile.deleteOnExit();
    try (FileWriter writer = new FileWriter(testFile)) {
      writer.write(testFileContents);
    }

    doThrow(IOException.class).when(catalogCsvReader).read(any());

    Assertions.assertThrows(IOException.class, () -> catalogCsvLoader.load(testFile));
  }

  /** Tests for {@link CatalogCsvLoader#load(InputStream)} */
  @Test
  public void testLoadWithInputStreamProperlyPassesInputStreamToCatalogCsvReader()
      throws Exception {
    InputStream inputStream = ByteSource.wrap("Test Bytes, Please Ignore".getBytes()).openStream();

    catalogCsvLoader.load(inputStream);

    verify(catalogCsvReader).read(inputStream);
  }

  @Test
  public void testLoadWithInputStreamProperlySavesResultFromReaderAfterRead() throws Exception {
    InputStream inputStream = ByteSource.wrap("Test Bytes, Please Ignore".getBytes()).openStream();

    catalogCsvLoader.load(inputStream);

    verify(this.tagEngine).saveAll(this.catalogCsvReader.getTags());
    verify(this.authorEngine).saveAll(this.catalogCsvReader.getAuthors());
    verify(this.shelfEngine).saveAll(this.catalogCsvReader.getShelves());
    verify(this.bookEngine).saveAll(this.catalogCsvReader.getBooks());
  }

  @Test
  public void testLoadWithInputStreamThrowsExceptionsThrownByCsvReader() throws Exception {
    InputStream inputStream = ByteSource.wrap("Test Bytes, Please Ignore".getBytes()).openStream();

    doThrow(IOException.class).when(catalogCsvReader).read(any());

    Assertions.assertThrows(IOException.class, () -> catalogCsvLoader.load(inputStream));
  }
}
