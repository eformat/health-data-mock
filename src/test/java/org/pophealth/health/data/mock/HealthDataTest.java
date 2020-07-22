package org.pophealth.health.data.mock;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HealthDataTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/healthData")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}