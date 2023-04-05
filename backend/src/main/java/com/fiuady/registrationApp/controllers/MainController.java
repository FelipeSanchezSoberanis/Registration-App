package com.fiuady.registrationApp.controllers;

import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
public class MainController {
    @GetMapping("/")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hi", HttpStatus.OK);
    }
}
