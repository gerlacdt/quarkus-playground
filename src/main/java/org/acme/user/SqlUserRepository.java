package org.acme.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class SqlUserRepository implements UserRepository {

  @Inject EntityManager em;

  @Inject PanacheRepository<User> repo;

  public void persist(User user) {
    repo.persist(user);
  }

  public User update(User u) {
    em.merge(u);
    return u;
  }

  public List<User> findAll() {
    return repo.findAll().list();
  }

  public Optional<User> findById(Long id) {
    return repo.findByIdOptional(id);
  }

  public boolean deleteById(Long id) {
    return repo.deleteById(id);
  }

  public long deleteAll() {
    return repo.deleteAll();
  }
}
