package school.raikes.library.libraryserver.engine;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.IAuthorAccessor;
import school.raikes.library.libraryserver.model.entity.Author;

/** Basic implementation of the {@link IAuthorEngine} interface. */
@Service
public class AuthorEngine implements IAuthorEngine {
  private final IAuthorAccessor authorAccessor;

  @Autowired
  public AuthorEngine(IAuthorAccessor authorAccessor) {
    this.authorAccessor = authorAccessor;
  }

  @Override
  public Iterable<Author> findAll() {
    return this.authorAccessor.findAll();
  }

  @Override
  public Map<String, Author> loadNameAuthorMap() {
    Map<String, Author> authorMap = new HashMap<>();

    findAll()
        .forEach((a) -> authorMap.put(a.getFirstName() + a.getMiddleName() + a.getLastName(), a));

    return authorMap;
  }

  @Override
  public Author save(Author author) {
    return this.authorAccessor.save(author);
  }

  @Override
  public Iterable<Author> saveAll(Iterable<Author> authors) {
    return this.authorAccessor.saveAll(authors);
  }

  @Override
  public void delete(Author author) {
    this.authorAccessor.delete(author);
  }
}
