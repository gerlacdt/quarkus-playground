package org.acme.user;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class UserResourceFakeTest {

  private Logger log = LoggerFactory.getLogger(UserResourceFakeTest.class);

  @Inject UserRepository fakeRepo;

  @BeforeEach
  public void beforeEach() {
    fakeRepo.deleteAll();
    var user1 = createDefaultUser();
    var user2 = createDefaultUser();
    user2.username = "danger";
    var user3 = createDefaultUser();
    user3.username = "foobar";
    fakeRepo.persist(user1);
    fakeRepo.persist(user2);
    fakeRepo.persist(user3);
  }

  @Test
  public void getAll_user_ok() {
    var response =
        given()
            .when()
            .get("/users")
            .then()
            .contentType(ContentType.JSON)
            .extract()
            .as(GetUsersResponse.class);

    var expected = 3;
    assertEquals(expected, response.getUsers().size());
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
