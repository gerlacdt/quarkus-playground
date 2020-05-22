package org.acme.user;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserService {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  @Inject UserRepository userRepository;

  @Transactional
  public User save(User u) {
    userRepository.persist(u);
    return u;
  }

  @Transactional
  public User update(User u) {
    return userRepository.update(u);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Transactional
  public boolean deleteById(Long id) {
    return userRepository.deleteById(id);
  }
}
