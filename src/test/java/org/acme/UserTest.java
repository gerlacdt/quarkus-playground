package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

@QuarkusTest
public class UserTest {

    @BeforeEach
    @Transactional
    public void beforeEach() {
        User.deleteAll();
    }

    @Test
    @Transactional
    public void save_user_ok() {
        var user = createDefaultUser();
        user.persist();
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
