package com.hepsiemlak.TodoApp.service.impl;

import com.hepsiemlak.TodoApp.dto.LoginDTO;
import com.hepsiemlak.TodoApp.dto.UserResponseDTO;
import com.hepsiemlak.TodoApp.service.SessionService;
import com.hepsiemlak.TodoApp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SessionServiceImpl implements SessionService {

  UserService userService;

  @Override
  @Cacheable("UserLogin")
  public ResponseEntity<String> login(LoginDTO loginDTO) {
    UserResponseDTO user = userService.findUserByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

    if (user != null) {
      return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
  }
}
