package com.hiberus.controllers;

import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaReadMapper;
import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaReadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(pizzaToResponseDto(pizzaReadService.getPizzas()));
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

    @GetMapping("/favourites")
    @ApiOperation(value = "Get user's favourite pizzas")
    ResponseEntity<List<PizzaResponseDto>> getFavouritePizzas(@RequestParam List<Long> pizzaIds) {
        return ResponseEntity.ok(pizzaToResponseDto(pizzaReadService.getFavouritePizzas(pizzaIds)));
    }

    private List<PizzaResponseDto> pizzaToResponseDto(List<Pizza> pizzas) {
        return pizzas.stream()
                .map(pizzaReadMapper::pizzaToResponseDto)
                .toList();
    }

}
