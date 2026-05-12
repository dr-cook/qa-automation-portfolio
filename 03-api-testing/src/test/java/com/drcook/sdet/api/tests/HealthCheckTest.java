package com.drcook.sdet.api.tests;

import com.drcook.sdet.api.config.ApiConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Smoke tests — verifica que la API está disponible.
 * Target: JSONPlaceholder (https://jsonplaceholder.typicode.com)
 */
public class HealthCheckTest {

    @BeforeClass
    public void setup() {
        io.restassured.RestAssured.requestSpecification = ApiConfig.getBaseSpec();
    }

    @Test(groups = {"smoke", "P1"})
    public void api_isReachable_returns200() {
        given()
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .time(lessThan(5000L));
    }

    @Test(groups = {"smoke", "P1"})
    public void api_response_hasJsonContentType() {
        given()
            .when()
                .get("/users")
            .then()
                .contentType(containsString("json"));
    }

    @Test(groups = {"smoke", "P2"})
    public void api_unknownEndpoint_returns404() {
        given()
            .when()
                .get("/endpoint-inexistente")
            .then()
                .statusCode(404);
    }
}
