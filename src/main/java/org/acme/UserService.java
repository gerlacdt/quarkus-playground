package org.acme;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserService {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  @Inject EntityManager em;

  @Inject UserRepository userRepository;

  @Transactional
  public User save(User u) {
    userRepository.persist(u);
    return u;
  }

  @Transactional
  public User update(User u) {
    em.merge(u);
    return u;
  }

  public List<User> findAll() {
    return userRepository.findAll().list();
  }

  public User findById(Long id) {
    return userRepository.findById(id);
  }

  @Transactional
  public boolean deleteById(Long id) {
    return userRepository.deleteById(id);
  }
}
