package com.hepsiemlak.TodoApp.repository;


import com.hepsiemlak.TodoApp.model.User;
import java.util.Optional;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface UserRepository extends CouchbaseRepository<User, String> {
  Optional<User> findByEmailAndPassword(String email, String password);
  Boolean existsByEmail(String email);
}
