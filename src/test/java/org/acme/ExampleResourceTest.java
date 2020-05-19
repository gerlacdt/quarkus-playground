package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ExampleResourceTest {

  @Test
  public void testHelloEndpoint() {
    var response =
        given()
            .when()
            .get("/hello")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(200)
            .extract()
            .as(HelloResponse.class);

    assertEquals(new HelloResponse("hello"), response);
  }

  @Test
  public void hello_post_ok() {
    var response =
        given()
            .when()
            .contentType(ContentType.JSON)
            .body(new HelloRequest("foo"))
            .post("/hello")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(HelloResponse.class);

    assertEquals(new HelloResponse("foo"), response);
  }
}
