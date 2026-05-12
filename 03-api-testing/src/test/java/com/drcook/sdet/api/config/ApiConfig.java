package com.drcook.sdet.api.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Configuración base para todos los tests de API.
 * Centraliza baseURI, headers, autenticación y logging.
 */
public class ApiConfig {

    // Usa variable de entorno o fallback a la API pública de pruebas
    private static final String BASE_URL = System.getenv("API_BASE_URL") != null
            ? System.getenv("API_BASE_URL")
            : "https://reqres.in";

    private static final String API_KEY = System.getenv("API_KEY") != null
            ? System.getenv("API_KEY")
            : "";

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public static RequestSpecification getAuthSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpec())
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();
    }
}
