package com.hepsiemlak.TodoApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequestDTO implements Serializable {
  @NotBlank
  @Schema(name = "name", example = "Ali", required = true)
  String name;
  @Schema(name = "email", example = "aa@gmail.com", required = true)
  @Email
  String email;
  @Schema(name = "password", example = "abc12345", required = true)
  @NotBlank
  String password;
}
