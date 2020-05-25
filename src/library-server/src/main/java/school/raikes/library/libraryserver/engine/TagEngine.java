package school.raikes.library.libraryserver.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.ITagAccessor;
import school.raikes.library.libraryserver.model.entity.Tag;

/** Basic implementation of the {@link ITagEngine} interface. */
@Service
public class TagEngine implements ITagEngine {
  private final ITagAccessor tagAccessor;

  @Autowired
  public TagEngine(ITagAccessor tagAccessor) {
    this.tagAccessor = tagAccessor;
  }

  @Override
  public Iterable<Tag> findAll() {
    return this.tagAccessor.findAll();
  }

  @Override
  public Optional<Tag> findByName(String name) {
    return this.tagAccessor.findByName(name);
  }

  @Override
  public Map<String, Tag> getNameTagMap() {
    Map<String, Tag> tagMap = new HashMap<>();

    findAll().forEach((t) -> tagMap.put(t.getName(), t));

    return tagMap;
  }

  @Override
  public Tag save(Tag tag) {
    return this.tagAccessor.save(tag);
  }

  @Override
  public Iterable<Tag> saveAll(Iterable<Tag> tags) {
    return this.tagAccessor.saveAll(tags);
  }

  @Override
  public void delete(Tag tag) {
    this.tagAccessor.delete(tag);
  }
}
