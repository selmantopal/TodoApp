package com.hepsiemlak.TodoApp.service.impl;

import com.couchbase.client.core.error.UserNotFoundException;
import com.hepsiemlak.TodoApp.dto.UserRequestDTO;
import com.hepsiemlak.TodoApp.dto.UserResponseDTO;
import com.hepsiemlak.TodoApp.exception.MailAlreadyExistException;
import com.hepsiemlak.TodoApp.model.User;
import com.hepsiemlak.TodoApp.repository.UserRepository;
import com.hepsiemlak.TodoApp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {
  UserRepository userRepository;

  @Override
  public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
    if (userRepository.existsByEmail(userRequestDTO.getEmail()).equals(true))
      throw new MailAlreadyExistException("mail already exist: " + userRequestDTO.getEmail());

    User newUser = userDtoToUser(userRequestDTO);
    userRepository.save(newUser);
    return userToUserResponse(newUser);
  }

  @Override
  public UserResponseDTO editUser(String id, UserRequestDTO userRequestDTO) {
    if (userRepository.existsByEmail(userRequestDTO.getEmail()).equals(true))
      throw new MailAlreadyExistException("mail already exist: " + userRequestDTO.getEmail());

    User exitUser = getUserById(id);
    User user = userDtoToUser(userRequestDTO);
    user.setId(exitUser.getId());
    user = userRepository.save(user);
    return userToUserResponse(user);
  }

  @Override
  public void removeUser(String id) {
    userRepository.delete(getUserById(id));
  }

  @Override
  public UserResponseDTO getUser(String id) {
    User user = getUserById(id);
    return userToUserResponse(user);
  }

  @Override
  public UserResponseDTO findUserByEmailAndPassword(String mail, String password) {
    User user = userRepository
        .findByEmailAndPassword(mail, password)
        .orElseThrow(() -> new UserNotFoundException("User Service", "There is no user for this email: " + mail));
    return userToUserResponse(user);
  }

  public User userDtoToUser(UserRequestDTO userRequestDTO) {
    User user = new User();
    user.setName(userRequestDTO.getName());
    user.setPassword(userRequestDTO.getPassword());
    user.setEmail(userRequestDTO.getEmail());

    return user;
  }

  public UserResponseDTO userToUserResponse(User newUser) {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setUserId(newUser.getId());
    userResponseDTO.setName(newUser.getName());

    return userResponseDTO;
  }

  private User getUserById(String id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User Service", "There is no user for this id: " + id));
  }
}
