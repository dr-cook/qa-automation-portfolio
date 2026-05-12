package com.drcook.sdet.api.tests;

import com.drcook.sdet.api.config.ApiConfig;
import com.drcook.sdet.api.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Suite de tests para User API.
 * Cubre CRUD completo + casos negativos + DataProvider para escenarios múltiples.
 *
 * TODO para el reto de la semana:
 *   1. Implementar createUser_withInvalidPayload_returns400()
 *   2. Implementar updateUser_withPatch_updatesOnlySpecifiedFields()
 *   3. Agregar validación de JSON Schema en getUser_validResponse_matchesSchema()
 */
public class UserApiTest {

    @BeforeClass
    public void setup() {
        io.restassured.RestAssured.requestSpecification = ApiConfig.getBaseSpec();
    }

    // ─────────────────────────────────────────
    // GET Tests
    // ─────────────────────────────────────────

    @Test(groups = {"smoke", "P1"})
    @Description("GET /users retorna lista paginada con datos válidos")
    @Severity(SeverityLevel.CRITICAL)
    public void getUsers_returnsPagedList_withValidStructure() {
        given()
            .queryParam("page", 1)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("page", equalTo(1))
            .body("data", not(empty()))
            .body("data[0].id", notNullValue())
            .body("data[0].email", containsString("@"))
            .body("data[0].first_name", notNullValue());
    }

    @Test(groups = {"regression", "P1"})
    @Description("GET /users/{id} retorna usuario específico")
    @Severity(SeverityLevel.CRITICAL)
    public void getUser_withValidId_returnsCorrectUser() {
        int userId = 2;
        given()
            .pathParam("id", userId)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("data.id", equalTo(userId))
            .body("data.email", notNullValue())
            .body("data.first_name", notNullValue())
            .body("data.last_name", notNullValue());
    }

    @Test(groups = {"regression", "P2"})
    @Description("GET /users/{id} con ID inexistente retorna 404")
    @Severity(SeverityLevel.NORMAL)
    public void getUser_withInvalidId_returns404() {
        given()
            .pathParam("id", 9999)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(404)
            .body(emptyString());
    }

    // ─────────────────────────────────────────
    // POST Tests
    // ─────────────────────────────────────────

    @Test(groups = {"regression", "P1"})
    @Description("POST /users crea un usuario y retorna 201 con ID generado")
    @Severity(SeverityLevel.CRITICAL)
    public void createUser_withValidPayload_returns201WithId() {
        User newUser = User.builder()
                .name("Douglas Cook")
                .job("SDET Engineer")
                .build();

        given()
            .body(newUser)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", equalTo("Douglas Cook"))
            .body("job", equalTo("SDET Engineer"))
            .body("createdAt", notNullValue());
    }

    // ─────────────────────────────────────────
    // PUT Tests
    // ─────────────────────────────────────────

    @Test(groups = {"regression", "P2"})
    @Description("PUT /users/{id} actualiza usuario completo y retorna 200")
    @Severity(SeverityLevel.NORMAL)
    public void updateUser_withValidPayload_returns200() {
        User updatedUser = User.builder()
                .name("Douglas Cook")
                .job("Senior SDET")
                .build();

        given()
            .pathParam("id", 2)
            .body(updatedUser)
        .when()
            .put("/users/{id}")
        .then()
            .statusCode(200)
            .body("name", equalTo("Douglas Cook"))
            .body("job", equalTo("Senior SDET"))
            .body("updatedAt", notNullValue());
    }

    // ─────────────────────────────────────────
    // DELETE Tests
    // ─────────────────────────────────────────

    @Test(groups = {"regression", "P2"})
    @Description("DELETE /users/{id} elimina usuario y retorna 204")
    @Severity(SeverityLevel.NORMAL)
    public void deleteUser_withValidId_returns204() {
        given()
            .pathParam("id", 2)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(204)
            .body(emptyString());
    }

    // ─────────────────────────────────────────
    // DataProvider — múltiples páginas
    // ─────────────────────────────────────────

    @Test(dataProvider = "pageNumbers", groups = {"regression", "P2"})
    @Description("GET /users con distintas páginas — verifica paginación")
    @Severity(SeverityLevel.NORMAL)
    public void getUsers_multiplePages_allReturn200(int page, int expectedPerPage) {
        given()
            .queryParam("page", page)
            .queryParam("per_page", expectedPerPage)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("page", equalTo(page))
            .body("data", not(empty()));
    }

    @DataProvider(name = "pageNumbers")
    public Object[][] pageNumbers() {
        return new Object[][] {
            {1, 6},
            {2, 6},
        };
    }
}
