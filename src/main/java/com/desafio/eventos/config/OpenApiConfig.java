package com.desafio.eventos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development"),
                        new Server()
                                .url("http://localhost:3000")
                                .description("Frontend Development")
                ))
                .components(new Components())
                .info(new Info()
                        .title("Eventos API")
                        .version("1.0.0")
                        .description("API RESTful para gerenciar eventos")
                        .contact(new Contact()
                                .name("Eventos Support")
                                .email("support@eventos.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

