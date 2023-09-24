package com.hiberus.controllers;

import com.hiberus.dtos.PizzaCreateDto;
import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.dtos.PizzaUpdateDto;
import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaWriteMapper;
import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaWriteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pizzas")
public class PizzaWriteController {

    @Autowired
    private PizzaWriteService pizzaWriteService;

    @Autowired
    private PizzaWriteMapper pizzaWriteMapper;

    @PostMapping
    @ApiOperation(value = "Create a new pizza")
    public ResponseEntity<PizzaResponseDto> createPizza(@RequestBody PizzaCreateDto pizzaCreateDto) {
        try {
            return new ResponseEntity<>(pizzaWriteMapper.pizzaToResponseDto(
                    pizzaWriteService.createPizza(pizzaWriteMapper.dtoCreateToPizza(pizzaCreateDto))),
                    HttpStatus.CREATED);
        } catch (PizzaAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{pizzaId}")
    @ApiOperation(value = "Modify an existing pizza")
    public ResponseEntity<PizzaResponseDto> updatePizza(@PathVariable Long pizzaId, @RequestBody PizzaUpdateDto pizzaUpdateDto) {
       try {
           PizzaResponseDto pizza = pizzaWriteMapper.pizzaToResponseDto(pizzaWriteService
                   .updatePizza(pizzaId, pizzaWriteMapper.dtoUpdateToPizza(pizzaUpdateDto)));
           return ResponseEntity.ok(pizza);
        } catch (PizzaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
