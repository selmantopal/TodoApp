package com.hepsiemlak.TodoApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;


@EnableCouchbaseRepositories
@Configuration
public class CouchbaseConfig {
}