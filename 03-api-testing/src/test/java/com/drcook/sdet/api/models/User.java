package com.drcook.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo de User usando el patrón Builder manual.
 * Sin Lombok — compatible con Java 25.
 *
 * Uso:
 *   User user = new User.Builder()
 *       .name("Douglas Cook")
 *       .job("SDET Engineer")
 *       .build();
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Integer id;
    private String name;
    private String job;
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String avatar;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    // Constructor privado — solo el Builder puede instanciar
    private User() {}

    // Getters
    public Integer getId()        { return id; }
    public String getName()       { return name; }
    public String getJob()        { return job; }
    public String getEmail()      { return email; }
    public String getFirstName()  { return firstName; }
    public String getLastName()   { return lastName; }
    public String getAvatar()     { return avatar; }
    public String getCreatedAt()  { return createdAt; }
    public String getUpdatedAt()  { return updatedAt; }

    // ─────────────────────────
    // Builder Pattern
    // ─────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final User user = new User();

        public Builder name(String name)   { user.name = name;  return this; }
        public Builder job(String job)     { user.job = job;    return this; }
        public Builder email(String email) { user.email = email; return this; }

        public User build() { return user; }
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', job='" + job + "'}";
    }
}
