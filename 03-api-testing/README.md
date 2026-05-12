# 03 — API Testing con RestAssured + TestNG

> Módulo de API testing del portafolio SDET de [dr-cook](https://github.com/dr-cook)

## Stack

| Herramienta | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje base |
| RestAssured | 5.4.0 | HTTP client para tests de API |
| TestNG | 7.9.0 | Test runner con grupos y DataProviders |
| Allure | 2.25.0 | Reportes visuales |
| Lombok | 1.18.32 | Reducir boilerplate en POJOs |
| Jackson | 2.17.0 | Serialización JSON |

## Estructura

```
03-api-testing/
├── src/test/java/com/drcook/sdet/api/
│   ├── config/
│   │   └── ApiConfig.java          # Base spec, auth, logging
│   ├── models/
│   │   └── User.java               # POJO con Lombok Builder
│   └── tests/
│       ├── HealthCheckTest.java    # Smoke tests de infraestructura
│       └── UserApiTest.java        # CRUD + DataProvider + casos negativos
├── src/test/resources/schemas/
│   └── user-schema.json            # JSON Schema para validación
├── .github/workflows/api-tests.yml # CI/CD Pipeline
├── testng.xml                      # Suite con grupos smoke/regression
└── pom.xml
```

## Correr los tests

```bash
# Solo smoke tests (rápido, para CI)
mvn test -Dgroups=smoke

# Suite completa
mvn test

# Reporte Allure
mvn allure:serve
```

## Variables de entorno

| Variable | Default | Descripción |
|---|---|---|
| `API_BASE_URL` | `https://reqres.in` | URL base de la API |
| `API_KEY` | _(vacío)_ | Token de autenticación |

## Reto pendiente (TODO)

- [ ] Implementar `createUser_withInvalidPayload_returns400()`
- [ ] Agregar validación de JSON Schema en `getUser_validResponse_matchesSchema()`
- [ ] Implementar `updateUser_withPatch_updatesOnlySpecifiedFields()`
- [ ] Agregar test de autenticación: token inválido → 401

---

**Parte del portafolio:** [qa-automation-portfolio](https://github.com/dr-cook/qa-automation-portfolio)
