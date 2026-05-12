package com.drcook.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * POJO que representa un User en la API.
 * Usar @Builder para construir test data fluentemente.
 *
 * Ejemplo:
 *   User user = User.builder()
 *       .name("Douglas Cook")
 *       .job("SDET")
 *       .build();
 */
@Data
@Builder
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
}
