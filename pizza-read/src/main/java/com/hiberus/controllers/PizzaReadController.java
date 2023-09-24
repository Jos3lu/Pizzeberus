package com.hiberus.controllers;

import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaReadMapper;
import com.hiberus.services.PizzaReadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pizzas")
public class PizzaReadController {

    @Autowired
    private PizzaReadService pizzaReadService;

    @Autowired
    private PizzaReadMapper pizzaReadMapper;

    @GetMapping
    @ApiOperation(value = "Get pizzas")
    public ResponseEntity<List<PizzaResponseDto>> getPizzas() {
        List<PizzaResponseDto> pizzas = pizzaReadService.getPizzas()
                .stream()
                .map(pizzaReadMapper::pizzaToResponseDto)
                .toList();

        return ResponseEntity.ok(pizzas);
    }

    @GetMapping(value = "/{pizzaId}")
    @ApiOperation(value = "Get pizza by ID")
    public ResponseEntity<PizzaResponseDto> getPizza(@PathVariable Long pizzaId) {
        try {
            return ResponseEntity.ok(pizzaReadMapper.pizzaToResponseDto(
                    pizzaReadService.getPizza(pizzaId)));
        } catch (PizzaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
