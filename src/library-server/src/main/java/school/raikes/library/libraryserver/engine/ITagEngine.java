package school.raikes.library.libraryserver.engine;

import java.util.Map;
import java.util.Optional;
import school.raikes.library.libraryserver.model.entity.Tag;

/** Engine interface for managing {@link Tag}s */
public interface ITagEngine {
  Iterable<Tag> findAll();

  Optional<Tag> findByName(String name);

  Map<String, Tag> loadNameTagMap();

  Tag save(Tag tag);

  Iterable<Tag> saveAll(Iterable<Tag> tags);

  void delete(Tag tag);
}
