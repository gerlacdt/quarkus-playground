package org.acme.middleware;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.Test;

public class MetricsResponseFilterTest {

  @Test
  public void toTemplateUri_onePathParam_ok() {
    var uri = "/users/12";
    var pathParams = new MultivaluedHashMap<String, String>();
    pathParams.add("id", "12");

    var actual = MetricsResponseFilter.toTemplateUri(uri, pathParams);

    var expected = "/users/{id}";
    assertEquals(expected, actual);
  }

  @Test
  public void toTemplateUri_multiplePathParam_ok() {
    var uri = "/users/12/contracts/abcdef/validations/42";
    var pathParams = new MultivaluedHashMap<String, String>();
    pathParams.add("id", "12");
    pathParams.add("contractId", "abcdef");
    pathParams.add("validationId", "42");

    var actual = MetricsResponseFilter.toTemplateUri(uri, pathParams);

    var expected = "/users/{id}/contracts/{contractId}/validations/{validationId}";
    assertEquals(expected, actual);
  }

  @Test
  public void toTemplateUri_nomatch_ok() {
    var uri = "/users/12/contracts/abcdef";
    var pathParams = new MultivaluedHashMap<String, String>();

    var actual = MetricsResponseFilter.toTemplateUri(uri, pathParams);

    var expected = "/users/12/contracts/abcdef";
    assertEquals(expected, actual);
  }
}
