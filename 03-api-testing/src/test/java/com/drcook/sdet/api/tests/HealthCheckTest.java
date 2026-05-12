package com.drcook.sdet.api.tests;

import com.drcook.sdet.api.config.ApiConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * TC-HEALTH-001 al TC-HEALTH-003
 * Verifica que la API está disponible y responde correctamente.
 * Equivalente a un smoke test de infraestructura.
 */
public class HealthCheckTest {

    @BeforeClass
    public void setup() {
        io.restassured.RestAssured.requestSpecification = ApiConfig.getBaseSpec();
    }

    @Test(groups = {"smoke", "P1"})
    @Description("Verifica que el endpoint /users retorna 200 OK")
    @Severity(SeverityLevel.BLOCKER)
    public void api_isReachable_returns200() {
        given()
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .time(lessThan(3000L)); // NFR: respuesta < 3 segundos
    }

    @Test(groups = {"smoke", "P1"})
    @Description("Verifica que el Content-Type de respuesta es application/json")
    @Severity(SeverityLevel.CRITICAL)
    public void api_response_hasJsonContentType() {
        given()
            .when()
                .get("/users")
            .then()
                .contentType("application/json");
    }

    @Test(groups = {"smoke", "P2"})
    @Description("Verifica que un endpoint inexistente retorna 404")
    @Severity(SeverityLevel.NORMAL)
    public void api_unknownEndpoint_returns404() {
        given()
            .when()
                .get("/endpoint-inexistente")
            .then()
                .statusCode(404);
    }
}
