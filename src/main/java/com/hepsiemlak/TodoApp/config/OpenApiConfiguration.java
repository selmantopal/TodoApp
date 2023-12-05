package com.hepsiemlak.TodoApp.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(info = @Info(title = "Spring Boot TODO App For Hepsi Emlak", version = "0.0.1"))
public class OpenApiConfiguration {

}
