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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Create new pizza")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 409, message = "Already exists")
    })
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
    @ApiOperation(value = "Update an existing pizza")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Not found")
    })
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
