package org.acme.restclient;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class CountryGatewayTest {

    @Inject
    @RestClient
    private CountryGateway countryGateway;

    @Test
    public void getByName_Germany_ok() {
        var actual = countryGateway.getByName("Germany");

        assertEquals(1, actual.size());
        var actualGermany = actual.get(0);
        assertEquals("Berlin", actualGermany.capital);
    }

// default exception handling
//    @Test
//    public void getByName_unknownCountry_fail() {
//        Exception exception = assertThrows(WebApplicationException.class, () ->
//                countryGateway.getByName("foobar")  // return 404
//        );
//        assertEquals("Unknown error, status code 404", exception.getMessage());
//    }

    @Test
    public void getByName_unknownCountry_fail() {
        Exception exception = assertThrows(WebApplicationException.class, () ->
                countryGateway.getByName("foobar")  // return 404
        );
        assertEquals("{\"status\":404,\"message\":\"Not Found\"}", exception.getMessage());
    }
}
