package com.hepsiemlak.TodoApp.service;

import com.hepsiemlak.TodoApp.dto.TodoRequestDTO;
import com.hepsiemlak.TodoApp.dto.TodoResponseDTO;
import java.util.List;

public interface TodoService {
  TodoResponseDTO addTodo(TodoRequestDTO todoRequestDTO);

  TodoResponseDTO editTodo(String todoId, TodoRequestDTO todoRequestDTO);

  void removeTodo(String userId, String todoId);

  TodoResponseDTO getUserTodo(String userId, String todoId);

  TodoResponseDTO todoComplete(String todoId);

  List<TodoResponseDTO> getUserTodoList(String userId);
}