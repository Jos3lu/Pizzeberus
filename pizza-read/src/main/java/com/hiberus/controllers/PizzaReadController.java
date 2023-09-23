package com.hiberus.controllers;

import com.hiberus.dtos.PizzaDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaMapper;
import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/pizzas")
public class PizzaReadController {

    private final PizzaReadService pizzaReadService;
    private final PizzaMapper pizzaMapper;

    public PizzaReadController(PizzaReadService pizzaReadService, PizzaMapper pizzaMapper) {
        this.pizzaReadService = pizzaReadService;
        this.pizzaMapper = pizzaMapper;
    }

    @GetMapping
    public ResponseEntity<List<PizzaDto>> getPizzas() {
        List<PizzaDto> pizzas = pizzaReadService.getPizzas()
                .stream()
                .map(pizza -> pizzaMapper.pizzaToDto(pizza))
                .toList();

        return ResponseEntity.ok(pizzas);
    }

    @GetMapping(value = "/{pizzaId}")
    public ResponseEntity<PizzaDto> getPizza(@PathVariable Long pizzaId) {
        try {
            return ResponseEntity.ok(pizzaMapper.pizzaToDto(
                    pizzaReadService.getPizza(pizzaId)));
        } catch (PizzaNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
