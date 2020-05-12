package school.raikes.library.libraryserver.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.raikes.library.libraryserver.accessor.IAuthorAccessor;
import school.raikes.library.libraryserver.model.entity.Author;

/**
 * Basic implementation of the {@link IAuthorEngine} interface.
 */
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
  public Author save(Author author) {
    return this.authorAccessor.save(author);
  }

  @Override
  public void delete(Author author) {
    this.authorAccessor.delete(author);
  }
}
