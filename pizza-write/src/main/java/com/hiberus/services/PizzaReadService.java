package com.hiberus.services;

import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.models.Pizza;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

}
