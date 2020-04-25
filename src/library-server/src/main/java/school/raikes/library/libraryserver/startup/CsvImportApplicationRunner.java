package school.raikes.library.libraryserver.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import school.raikes.library.libraryserver.readers.CatalogCsvReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Component
@Profile("dev")
@Slf4j
public class CsvImportApplicationRunner implements ApplicationRunner {

    private static final String CSV_FILE_PARAMETER = "importcsvfile";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Checking for CSV catalog to import");
        if (args.containsOption(CSV_FILE_PARAMETER)) {
            String filepath = args.getOptionValues(CSV_FILE_PARAMETER).get(0);
            log.info("Loading CSV file: {}", filepath);

            File catalogFile = new File(filepath);
            CatalogCsvReader catalogCsvReader = new CatalogCsvReader();

            try {
                catalogCsvReader.read(catalogFile);
            } catch(IOException ioe) {
                log.warn("Error occurred while attempting to import CSV file {}", catalogFile);
                log.debug("Exception thrown: ", ioe);
            }

        } else {
            log.info("No import required");
        }
    }
}
