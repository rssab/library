package school.raikes.library.libraryserver.readers;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import school.raikes.library.libraryserver.model.entity.*;

import java.io.*;
import java.util.*;

@Component
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


    Map<Integer, Shelf> shelfMap;
    Map<String, Type> typeMap;
    Map<String, Tag> tagMap;
    Map<String, Author> authorMap;
    Map<Book, List<Copy>> bookCopyMap;
    Map<String, Book> isbnBookMap;

    public CatalogCsvReader() {
        this.shelfMap = new HashMap<>();
        this.typeMap = new HashMap<>();
        this.tagMap = new HashMap<>();
        this.authorMap = new HashMap<>();
        this.bookCopyMap = new HashMap<>();
        this.isbnBookMap = new HashMap<>();
    }

    /**
     * Reads a Catalog from a CSV file
     * @param file the CSV file
     * @throws IOException if any errors occur while reading the file or parsing the CSV.
     */
    public void read(File file) throws IOException {
        read(new FileInputStream(file));
    }

    /**
     * Reads a Catalog from a CSV input.
     * @param inputStream An {@link InputStream} for a CSV file.
     * @throws IOException if any errors occur while parsing the CSV.
     */
    public void read(InputStream inputStream) throws IOException {
        CSVParser parser = CSVParser.parse(inputStream, Charsets.UTF_8, CSVFormat.EXCEL.withFirstRecordAsHeader());

        for (CSVRecord row : parser) {

        }
    }

}
