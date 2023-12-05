package com.hepsiemlak.TodoApp.service;

import com.hepsiemlak.TodoApp.dto.LoginDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface SessionService {
  ResponseEntity<String> login(LoginDTO loginDTO);

  void logout(HttpServletRequest request, HttpServletResponse response);
}
