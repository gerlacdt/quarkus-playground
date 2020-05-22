package org.acme.user;

import io.quarkus.test.Mock;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mock
@ApplicationScoped
public class FakeUserRepository implements UserRepository {

  Logger log = LoggerFactory.getLogger(FakeUserRepository.class);

  private Map<Long, User> db = new HashMap<>();
  private AtomicLong counter = new AtomicLong(0);

  @Override
  public void persist(User user) {
    user.setId(counter.addAndGet(1));
    db.put(user.id, user);
  }

  @Override
  public User update(User u) {
    if (!this.db.containsKey(u.getId())) {
      throw new NotFoundException(String.format("Given userId %d does not exist.", u.getId()));
    }
    return this.db.replace(u.id, u);
  }

  @Override
  public List<User> findAll() {
    log.info("db.values() {}", db.values());
    return new ArrayList<User>(db.values());
  }

  @Override
  public Optional<User> findById(Long id) {
    if (this.db.containsKey(id)) {
      return Optional.of(db.get(id));
    }
    return Optional.empty();
  }

  @Override
  public boolean deleteById(Long id) {
    var isDeleted = this.db.remove(id);
    return true ? isDeleted != null : false;
  }

  @Override
  public long deleteAll() {
    var length = db.size();
    this.db = new HashMap<>();
    return length;
  }
}
