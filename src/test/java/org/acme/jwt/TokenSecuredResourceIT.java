package org.acme.jwt;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class TokenSecuredResourceIT {

  private static final Logger log = LoggerFactory.getLogger(TokenSecuredResourceIT.class);

  @Test
  public void getToken_valid_ok() {

    var actual =
        given()
            .when()
            .get("/secured/token")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(TokenReponse.class);

    assertNotNull(actual.getAccessToken());
  }

  @Test
  public void permitAll_valid_ok() {
    given()
        .when()
        .get("/secured/permit-all")
        .then()
        .contentType(ContentType.TEXT)
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void rolesAllowed_noJwt_fail() {
    given().when().get("/secured/roles-allowed").then().statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  public void rolesAllowed_withJwt_ok() {
    var tokenResponse =
        given()
            .when()
            .get("/secured/token")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(TokenReponse.class);

    var body =
        given()
            .header("Authorization", " Bearer " + tokenResponse.getAccessToken())
            .accept(ContentType.TEXT)
            .when()
            .get("/secured/roles-allowed")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.TEXT)
            .extract()
            .body()
            .asString();
  }
}
