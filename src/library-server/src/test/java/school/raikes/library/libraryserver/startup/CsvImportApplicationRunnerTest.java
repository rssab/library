package school.raikes.library.libraryserver.startup;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import school.raikes.library.libraryserver.loaders.CatalogCsvLoader;

/** Tests for the {@link CsvImportApplicationRunner} class. */
public class CsvImportApplicationRunnerTest {
  @Mock CatalogCsvLoader catalogCsvLoader;

  @InjectMocks CsvImportApplicationRunner csvImportApplicationRunner;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /** Tests for {@link CsvImportApplicationRunner#run(ApplicationArguments)} */
  @Test
  public void testCsvImportApplicationRunnerCallsLoaderWhenArgumentPassed() throws Exception {
    ApplicationArguments args =
        new DefaultApplicationArguments("--importcsvfile=/some/filepath.csv");
    this.csvImportApplicationRunner.run(args);
    verify(this.catalogCsvLoader).load(new File("/some/filepath.csv"));
  }

  @Test
  public void testCsvImportApplicationRunnerDoesNotCallLoaderWhenArgumentNotPassed()
      throws Exception {
    ApplicationArguments args = new DefaultApplicationArguments();
    this.csvImportApplicationRunner.run(args);
    verify(this.catalogCsvLoader, never()).load(isA(File.class));
  }

  @Test
  public void testCsvImportApplicationRunnerSquashesExceptionWhenErrorOccursWhileLoading()
      throws Exception {
    ApplicationArguments args =
        new DefaultApplicationArguments("--importcsvfile=/some/filepath.csv");
    doThrow(IOException.class).when(this.catalogCsvLoader).load(isA(File.class));
    this.csvImportApplicationRunner.run(args);
  }
}
