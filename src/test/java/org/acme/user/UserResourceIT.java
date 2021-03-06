package org.acme.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class UserResourceIT {
  private static final Logger log = LoggerFactory.getLogger(UserResourceIT.class);

  @Inject private UserRepository userRepository;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    userRepository.deleteAll();
  }

  @Test
  public void create_user_ok() {
    var user = createDefaultUser();
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(user)
        .post("/users")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  public void create_invalidUser_fail() {
    var user = createDefaultUser();
    user.email = null; // required parameter
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(user)
        .post("/users")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body(
            "message",
            is("createUser.u.email: Email may not be blank"),
            "code",
            is("INVALID_REQUEST_BODY"));
  }

  @Test
  public void getAll_user_ok() {
    var user = createDefaultUser();
    var user2 = createDefaultUser();
    user2.username = "danger";

    given()
        .when()
        .contentType(ContentType.JSON)
        .body(user)
        .post("/users")
        .then()
        .statusCode(HttpStatus.SC_CREATED);

    given()
        .when()
        .contentType(ContentType.JSON)
        .body(user2)
        .post("/users")
        .then()
        .statusCode(HttpStatus.SC_CREATED);

    var response =
        given()
            .when()
            .get("/users")
            .then()
            .contentType(ContentType.JSON)
            .extract()
            .as(GetUsersResponse.class);

    var expected = 2;
    assertEquals(expected, response.getUsers().size());
  }

  @Test
  public void get_user_ok() {
    var user = createDefaultUser();

    var response =
        given()
            .when()
            .contentType(ContentType.JSON)
            .body(user)
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(User.class);

    var actual =
        given()
            .when()
            .get(String.format("/users/%d", response.id))
            .then()
            .contentType(ContentType.JSON)
            .extract()
            .as(User.class);

    assertEquals(user.email, actual.email);
  }

  @Test
  public void delete_user_ok() {
    var user = createDefaultUser();
    var user2 = createDefaultUser();
    user2.username = "danger";

    var userToKeep =
        given()
            .when()
            .contentType(ContentType.JSON)
            .body(user)
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(User.class);

    var userToDelete =
        given()
            .when()
            .contentType(ContentType.JSON)
            .body(user2)
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(User.class);

    given()
        .when()
        .delete(String.format("/users/%d", userToDelete.id))
        .then()
        .statusCode(HttpStatus.SC_OK);

    var actual =
        given()
            .when()
            .get("/users")
            .then()
            .contentType(ContentType.JSON)
            .extract()
            .as(GetUsersResponse.class);

    var expected = 1;
    assertEquals(expected, actual.getUsers().size());
    var expectedId = userToKeep.id;
    assertEquals(expectedId, actual.getUsers().get(0).id);
  }

  @Test
  public void update_user_ok() {
    // arrange
    var user = createDefaultUser();
    var response =
        given()
            .when()
            .contentType(ContentType.JSON)
            .body(user)
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(User.class);
    response.email = "changed@email.com";
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(response)
        .put(String.format("/users/%d", response.id))
        .then()
        .statusCode(HttpStatus.SC_OK);

    // act
    var actual =
        given()
            .when()
            .get(String.format("/users/%d", response.id))
            .then()
            .contentType(ContentType.JSON)
            .extract()
            .as(User.class);

    // assert
    var expected = "changed@email.com";
    assertEquals(expected, actual.email);
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
