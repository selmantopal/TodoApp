package com.hepsiemlak.TodoApp.service;

import com.hepsiemlak.TodoApp.dto.UserRequestDTO;
import com.hepsiemlak.TodoApp.dto.UserResponseDTO;

public interface UserService {
  UserResponseDTO addUser(UserRequestDTO userRequestDTO);

  UserResponseDTO editUser(String id, UserRequestDTO userRequestDTO);

  void removeUser(String id);

  UserResponseDTO getUser(String id);

  UserResponseDTO findUserByEmailAndPassword(String mail, String password);
}
