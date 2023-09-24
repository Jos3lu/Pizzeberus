package com.hiberus.services;

import com.hiberus.dtos.PizzaResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "pizza-read")
public interface PizzaReadService {

    /**
     * Get pizza by ID
     *
     * @param pizzaId Pizza ID
     * @return PizzaResponseDto
     */
    @GetMapping(value = "/api/pizzas/{pizzaId}")
    ResponseEntity<PizzaResponseDto> getPizza(@PathVariable Long pizzaId);

    /**
     * Get user's favourite pizzas
     *
     * @param pizzaIds Pizza IDs
     * @return List<Pizza>
     */
    @GetMapping("/favourites")
    ResponseEntity<List<PizzaResponseDto>> getFavouritePizzas(@RequestParam List<Long> pizzaIds);

}
