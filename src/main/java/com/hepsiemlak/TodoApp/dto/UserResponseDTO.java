package com.hepsiemlak.TodoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponseDTO implements Serializable {
  @JsonProperty("userId")
  String userId;
  @JsonProperty("name")
  @Schema(name = "name", example = "Ali",required = true)
  @NotBlank
  String name;
  @JsonProperty("email")
  @Schema(name = "email", example = "aa@gmail.com",required = true)
  @Email
  String email;
}