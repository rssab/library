package school.raikes.library.libraryserver.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import school.raikes.library.libraryserver.model.entity.Author;
import school.raikes.library.libraryserver.model.entity.Checkout;

/**
 * Custom serializer for {@link Checkout} objects to handle recursive information as well as
 * structure everything clearly.
 */
@JsonComponent
public class CheckoutSerializer extends JsonSerializer<Checkout> {
  private static final String ID_FIELD_KEY = "id";
  private static final String BOOK_FIELD_KEY = "book";
  private static final String BOOK_FIELD_ISBN_KEY = "isbn";
  private static final String BOOK_FIELD_TITLE_KEY = "title";
  private static final String BOOK_FIELD_SUBTITLE_KEY = "subtitle";
  private static final String BOOK_FIELD_EDITION_KEY = "edition";
  private static final String BOOK_FIELD_AUTHORS_KEY = "authors";
  private static final String BOOK_FIELD_AUTHORS_FIRST_NAME_KEY = "firstName";
  private static final String BOOK_FIELD_AUTHORS_MIDDLE_NAME_KEY = "middleName";
  private static final String BOOK_FIELD_AUTHORS_LAST_NAME_KEY = "lastName";
  private static final String RECIPIENT_FIELD_KEY = "recipient";
  private static final String RECIPIENT_FIELD_NUID_KEY = "nuid";
  private static final String RECIPIENT_FIELD_FIRST_NAME_KEY = "firstName";
  private static final String RECIPIENT_FIELD_LAST_NAME_KEY = "lastName";
  private static final String CHECKOUT_DATE_FIELD_KEY = "checkoutDate";
  private static final String DUE_DATE_FIELD_KEY = "dueDate";
  private static final String CHECKIN_DATE_FIELD_KEY = "checkinDate";

  @Override
  public void serialize(Checkout value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeNumberField(ID_FIELD_KEY, value.getId());

    // Write Book Information
    gen.writeObjectFieldStart(BOOK_FIELD_KEY);
    gen.writeStringField(BOOK_FIELD_ISBN_KEY, value.getCopy().getBook().getIsbn());
    gen.writeStringField(BOOK_FIELD_TITLE_KEY, value.getCopy().getBook().getTitle());

    if (value.getCopy().getBook().getSubtitle() != null) {
      gen.writeStringField(BOOK_FIELD_SUBTITLE_KEY, value.getCopy().getBook().getSubtitle());
    }

    gen.writeNumberField(BOOK_FIELD_EDITION_KEY, value.getCopy().getBook().getEdition());
    gen.writeArrayFieldStart(BOOK_FIELD_AUTHORS_KEY);

    for (Author a : value.getCopy().getBook().getAuthors()) {
      gen.writeStartObject();

      if (a.getFirstName() != null) {
        gen.writeStringField(BOOK_FIELD_AUTHORS_FIRST_NAME_KEY, a.getFirstName());
      }

      if (a.getMiddleName() != null) {
        gen.writeStringField(BOOK_FIELD_AUTHORS_MIDDLE_NAME_KEY, a.getMiddleName());
      }

      if (a.getLastName() != null) {
        gen.writeStringField(BOOK_FIELD_AUTHORS_LAST_NAME_KEY, a.getLastName());
      }

      gen.writeEndObject();
    }

    gen.writeEndArray();
    gen.writeEndObject();

    // Write Recipient Information
    gen.writeObjectFieldStart(RECIPIENT_FIELD_KEY);

    gen.writeStringField(RECIPIENT_FIELD_NUID_KEY, value.getRecipient().getNuid());
    gen.writeStringField(RECIPIENT_FIELD_FIRST_NAME_KEY, value.getRecipient().getFirstName());
    gen.writeStringField(RECIPIENT_FIELD_LAST_NAME_KEY, value.getRecipient().getLastName());
    gen.writeEndObject();

    if (value.getCheckoutDate() != null) {
      gen.writeObjectField("checkoutDate", value.getCheckoutDate());
    }

    if (value.getDueDate() != null) {
      gen.writeObjectField("dueDate", value.getDueDate());
    }

    if (value.getCheckinDate() != null) {
      gen.writeObjectField("checkinDate", value.getCheckinDate());
    }

    gen.writeEndObject();
  }
}
