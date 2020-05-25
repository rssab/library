package school.raikes.library.libraryserver.engine;

import java.util.Map;
import java.util.Optional;
import school.raikes.library.libraryserver.model.entity.Tag;

/** Engine interface for managing {@link Tag}s */
public interface ITagEngine {
  Iterable<Tag> findAll();

  Optional<Tag> findByName(String name);

  /**
   * Retrieves all tags in a {@link Map} of the tag's name to the individual tag entity. This is
   * primarily used in the reading process where serialized data is imported into the system.
   */
  Map<String, Tag> getNameTagMap();

  Tag save(Tag tag);

  Iterable<Tag> saveAll(Iterable<Tag> tags);

  void delete(Tag tag);
}
