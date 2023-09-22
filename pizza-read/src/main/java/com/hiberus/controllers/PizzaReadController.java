package com.hiberus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class PizzaReadController {

    @GetMapping(value = "/pizzas/{pizzaId}")
    public ResponseEntity<Void> getPizzas() {
        return ResponseEntity.noContent().build();
    }

}
