package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BoomResourceTest {

  @Test
  public void get_boom_ThrowException() {
    var actual =
        given()
            .when()
            .get("/boom")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .extract()
            .as(ErrorResponse.class);

    var expected = new ErrorResponse();
    expected.setMessage("BOOM, request exploded");
    expected.setCode("INTERNAL_SERVER_ERROR");
    assertEquals(expected, actual);
  }
}
