package school.raikes.library.libraryserver.loaders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import school.raikes.library.libraryserver.readers.CatalogCsvReader;

@Component
@Lazy
@Slf4j
public class CatalogCsvLoader {

    private CatalogCsvReader catalogCsvReader;

    @Autowired
    public CatalogCsvLoader(CatalogCsvReader catalogCsvReader) {
        log.error("This was built.");
    }

}
