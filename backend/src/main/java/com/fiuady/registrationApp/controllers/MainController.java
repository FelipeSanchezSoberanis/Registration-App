package com.fiuady.registrationApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/{id}")
    public ResponseEntity<String> test(@PathVariable("id") Long id) {

        return new ResponseEntity<>(id.toString(), HttpStatus.OK);
    }
}
