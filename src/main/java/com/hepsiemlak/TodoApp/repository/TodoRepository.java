package com.hepsiemlak.TodoApp.repository;

import com.hepsiemlak.TodoApp.model.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface TodoRepository extends CouchbaseRepository<Todo, String> {
  List<Todo> findAllByUserId(String userId);

  Optional<Todo> findByIdAndUserId(String id, String userId);
}