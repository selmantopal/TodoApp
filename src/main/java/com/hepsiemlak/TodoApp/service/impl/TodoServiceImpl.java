package com.hepsiemlak.TodoApp.service.impl;

import com.hepsiemlak.TodoApp.dto.TodoRequestDTO;
import com.hepsiemlak.TodoApp.dto.TodoResponseDTO;
import com.hepsiemlak.TodoApp.exception.TodoNotFoundException;
import com.hepsiemlak.TodoApp.model.Todo;
import com.hepsiemlak.TodoApp.repository.TodoRepository;
import com.hepsiemlak.TodoApp.service.TodoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class TodoServiceImpl implements TodoService {

  TodoRepository todoRepository;

  @Override
  public TodoResponseDTO addTodo(TodoRequestDTO todoRequestDTO) {
    Todo todo = convertTodoModel(todoRequestDTO);
    todo.setUserId(todoRequestDTO.getUserId());
    todo.setCompleted(false);
    todoRepository.save(todo);

    return createTodoResponse(todo);
  }


  @Override
  @CachePut("cacheUserTodo")
  public TodoResponseDTO editTodo(String todoId, TodoRequestDTO todoRequestDTO) {
    Todo exitTodo = findTodo(todoRequestDTO.getUserId(), todoId);

    Todo newTodoContent = convertTodoModel(todoRequestDTO);
    newTodoContent.setId(exitTodo.getId());
    newTodoContent = todoRepository.save(newTodoContent);

    return createTodoResponse(newTodoContent);
  }

  @Override
  @CacheEvict("cacheUserTodo")
  public void removeTodo(String userId, String todoId) {
    Todo todo = findTodo(todoId, userId);
    todoRepository.delete(todo);
  }

  @Override
  @Cacheable("cacheUserTodo")
  public TodoResponseDTO getUserTodo(String userId, String todoId) {
    Todo todo = findTodo(todoId, userId);
    return createTodoResponse(todo);
  }

  @Override
  public TodoResponseDTO todoComplete(String todoId) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
    todo.setCompleted(true);
    todo = todoRepository.save(todo);

    return createTodoResponse(todo);
  }

  @Override
  public List<TodoResponseDTO> getUserTodoList(String userId) {
    List<Todo> todoList = todoRepository.findAllByUserId(userId);

    return todoList.stream()
        .map(todo -> createTodoResponse(todo))
        .collect(Collectors.toList());
  }

  private Todo findTodo(String todoId, String userId) {
    return todoRepository.findByIdAndUserId(todoId, userId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
  }

  public TodoResponseDTO createTodoResponse(Todo todo) {
    TodoResponseDTO todoResponseDTO = new TodoResponseDTO();
    todoResponseDTO.setId(todo.getId());
    todoResponseDTO.setTitle(todo.getTitle());
    todoResponseDTO.setDescription(todo.getDescription());
    todoResponseDTO.setDate(todo.getDate());
    todoResponseDTO.setCompleted(todo.getCompleted());

    return todoResponseDTO;
  }

  public Todo convertTodoModel(TodoRequestDTO todoRequestDTO) {
    Todo todo = new Todo();
    todo.setUserId(todoRequestDTO.getUserId());
    todo.setTitle(todoRequestDTO.getTitle());
    todo.setDate(todoRequestDTO.getDate());
    todo.setDescription(todoRequestDTO.getDescription());

    return todo;
  }
}
