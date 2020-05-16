package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserResourceTest {

    private static final Logger log = LoggerFactory.getLogger(UserResourceTest.class);

    @Inject
    private UserRepository userRepository;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void user_save_ok() {
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
    public void user_findAll_ok() {
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


        var response = given()
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
    public void user_findById_ok() {
        var user = createDefaultUser();

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(User.class);

        var actual = given()
                .when()
                .get(String.format("/users/%d", response.id))
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .as(GetSingleUserResponse.class);

        assertEquals(user.email, actual.getEmail());
    }

    @Test
    public void user_deleteById_ok() {
        var user = createDefaultUser();
        var user2 = createDefaultUser();
        user2.username = "danger";

        var userToKeep = given()
                .when()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(User.class);

        var userToDelete = given()
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

        var actual = given()
                .when()
                .get("/users")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .as(GetUsersResponse.class);

        var expected = 1;
        assertEquals(expected, actual.getUsers().size());
        var expectedId = userToKeep.id;
        assertEquals(expectedId, actual.getUsers().get(0).getId());
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
