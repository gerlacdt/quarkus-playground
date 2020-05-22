package org.acme.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
@TestMethodOrder(MethodOrderer.Random.class)
public class UserRepositoryIT {
  private static final Logger log = LoggerFactory.getLogger(UserRepositoryIT.class);

  @Inject private UserRepository userRepository;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    userRepository.deleteAll();
  }

  @Test
  @Transactional
  public void save_user_ok() {
    var user = createDefaultUser();
    userRepository.persist(user);

    log.info("save() id: {}", user.id);
  }

  @Test
  @Transactional
  public void findById_user_ok() {
    var user = createDefaultUser();
    userRepository.persist(user);

    var actual = userRepository.findById(user.id);

    log.info("findById() id: {}", user.id);

    var expected = user;
    assertEquals(expected, actual.get());
  }

  @Test
  @Transactional
  public void findAll_user_ok() {
    var user = createDefaultUser();
    userRepository.persist(user);
    var user2 = createDefaultUser();
    user2.email = "danger.mouse@gmail.com";
    userRepository.persist(user2);

    var actual = userRepository.findAll();
    log.info("findAll", actual);

    var expected = 2;
    assertEquals(expected, actual.size());
  }

  private User createDefaultUser() {
    var user = new User();
    user.age = 35;
    user.email = "john.doe@gmail.com";
    user.username = "John Doe";
    user.premium = true;
    return user;
  }
}
