package com.hepsiemlak.TodoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TodoRequestDTO implements Serializable {
  @JsonProperty("title")
  @Schema(name = "title", example = "Test", required = true)
  @NotNull
  String title;
  @Schema
  @JsonProperty("date")
  @NotNull
  LocalDate date;
  @JsonProperty("description")
  @Schema(name = "description", example = "HepsiEmlak")
  String description;
  @JsonProperty("userId")
  String userId;
}