package com.hepsiemlak.TodoApp.controller;

import com.hepsiemlak.TodoApp.dto.UserRequestDTO;
import com.hepsiemlak.TodoApp.dto.UserResponseDTO;
import com.hepsiemlak.TodoApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("v1/users")
@Tag(name = "users", description = "User Service")
public class UserController {
  UserService userService;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Sign Up")
  public UserResponseDTO signUp(@RequestBody @Valid UserRequestDTO user) {
    return userService.addUser(user);
  }

  @PutMapping("/user/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Edit User")
  public UserResponseDTO editUser(@RequestBody @Valid UserRequestDTO user, @PathVariable String userId) {
    return userService.editUser(userId, user);
  }

  @DeleteMapping("/{userId}/user")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Remove User")
  public void deleteUser(@PathVariable String userId) {
    userService.removeUser(userId);
  }

  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get User")
  public UserResponseDTO getUser(@PathVariable String userId) {
    return userService.getUser(userId);
  }
}