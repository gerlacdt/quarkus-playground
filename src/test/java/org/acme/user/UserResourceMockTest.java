package org.acme.user;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("NullAway")
@QuarkusTest
public class UserResourceMockTest {
  @InjectMock UserService userService;

  @BeforeEach
  public void beforeEach() {
    var user1 = createDefaultUser();
    var user2 = createDefaultUser();
    user2.username = "danger";
    Mockito.when(userService.findAll()).thenReturn(List.of(user1, user2));
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

    var expected = 2;
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
