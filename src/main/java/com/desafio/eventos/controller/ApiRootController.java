package com.desafio.eventos.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ApiRootController {

    @GetMapping({"/api", "/api/"})
    public ResponseEntity<Void> apiRoot(HttpServletRequest request) {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (accept != null && accept.contains("text/html")) {
            // Request from browser -> redirect to SPA root
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/"))
                    .build();
        }
        // Non-browser clients: no content
        return ResponseEntity.noContent().build();
    }
}
