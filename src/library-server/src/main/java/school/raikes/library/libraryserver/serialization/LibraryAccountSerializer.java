package school.raikes.library.libraryserver.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import school.raikes.library.libraryserver.model.entity.LibraryAccount;

import java.io.IOException;

/**
 * Custom serializer for {@link LibraryAccount} objects to ensure that encrypted passwords are not
 * sent along with the serialized object.
 */
@JsonComponent
public class LibraryAccountSerializer extends JsonSerializer<LibraryAccount> {

  public static final String ID_FIELD_KEY = "id";
  public static final String NUID_FIELD_KEY = "nuid";
  public static final String FIRST_NAME_FIELD_KEY = "firstName";
  public static final String LAST_NAME_FIELD_KEY = "lastName";

  @Override
  public void serialize(LibraryAccount value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeNumberField(ID_FIELD_KEY, value.getId());
    gen.writeStringField(NUID_FIELD_KEY, value.getNuid());
    gen.writeStringField(FIRST_NAME_FIELD_KEY, value.getFirstName());
    gen.writeStringField(LAST_NAME_FIELD_KEY, value.getLastName());
    gen.writeEndObject();
  }
}
