package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    @BeforeEach
    @Transactional
    public void beforeEach() {
        User.deleteAll();
    }

    @Test
    @Order(1)
    @Transactional
    public void save_user_ok() {
        var user = createDefaultUser();
        user.persist();

        log.info("save() id: {}", user.id);
    }

    @Test
    @Order(2)
    @Transactional
    public void user_findById_ok() {
        var user = createDefaultUser();
        user.persist();

        var actual = User.findById(user.id);

        log.info("findById() id: {}", user.id);

        var expected = user;
        assertEquals(expected, actual);
    }


    @Test
    @Order(3)
    @Transactional
    public void user_findAll_ok() {
        var user = createDefaultUser();
        user.persist();
        var user2 = createDefaultUser();
        user2.email = "danger.mouse@gmail.com";
        user2.persist();

        var actual = User.findAll();
        log.info("findAll", actual);

        var expected = 2;
        assertEquals(expected, actual.list().size());
    }


    public User createDefaultUser() {
        var user = new User();
        user.age = 35;
        user.email = "john.doe@gmail.com";
        user.username = "John Doe";
        user.premium = true;
        return user;
    }
}
