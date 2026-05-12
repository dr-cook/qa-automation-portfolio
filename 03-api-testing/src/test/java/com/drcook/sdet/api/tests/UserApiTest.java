package com.drcook.sdet.api.tests;

import com.drcook.sdet.api.config.ApiConfig;
import com.drcook.sdet.api.models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * CRUD tests contra JSONPlaceholder /users y /posts.
 *
 * TODO (tu reto de la semana):
 *   1. Implementar createPost_withInvalidPayload_returns400orIgnored()
 *   2. Implementar updateUser_withPatch_updatesOnlySpecifiedFields()
 *   3. Agregar validación de JSON Schema con user-schema.json
 */
public class UserApiTest {

    @BeforeClass
    public void setup() {
        io.restassured.RestAssured.requestSpecification = ApiConfig.getBaseSpec();
    }

    // ─── GET Tests ───

    @Test(groups = {"smoke", "P1"})
    public void getUsers_returnsList_withValidStructure() {
        given()
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("$", hasSize(10))
            .body("[0].id", notNullValue())
            .body("[0].email", containsString("@"))
            .body("[0].name", notNullValue());
    }

    @Test(groups = {"regression", "P1"})
    public void getUser_withValidId_returnsCorrectUser() {
        int userId = 1;
        given()
            .pathParam("id", userId)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(userId))
            .body("email", notNullValue())
            .body("name", notNullValue())
            .body("company.name", notNullValue());
    }

    @Test(groups = {"regression", "P2"})
    public void getUser_withInvalidId_returns404() {
        given()
            .pathParam("id", 9999)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(404);
    }

    // ─── POST Tests ───

    @Test(groups = {"regression", "P1"})
    public void createPost_withValidPayload_returns201() {
        String payload = "{\"title\":\"SDET Test Post\",\"body\":\"Testing from RestAssured\",\"userId\":1}";

        given()
            .body(payload)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("title", equalTo("SDET Test Post"));
    }

    // ─── PUT Tests ───

    @Test(groups = {"regression", "P2"})
    public void updatePost_withValidPayload_returns200() {
        String payload = "{\"id\":1,\"title\":\"Updated Title\",\"body\":\"Updated body\",\"userId\":1}";

        given()
            .pathParam("id", 1)
            .body(payload)
        .when()
            .put("/posts/{id}")
        .then()
            .statusCode(200)
            .body("title", equalTo("Updated Title"));
    }

    // ─── DELETE Tests ───

    @Test(groups = {"regression", "P2"})
    public void deletePost_withValidId_returns200() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/posts/{id}")
        .then()
            .statusCode(200);
    }

    // ─── DataProvider — múltiples usuarios ───

    @Test(dataProvider = "userIds", groups = {"regression", "P2"})
    public void getUser_multipleIds_allReturn200(int userId) {
        given()
            .pathParam("id", userId)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(userId));
    }

    @DataProvider(name = "userIds")
    public Object[][] userIds() {
        return new Object[][] {
            {1}, {2}, {5}, {10}
        };
    }
}
