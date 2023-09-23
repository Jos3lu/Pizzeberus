package com.hiberus.services;

import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.models.Pizza;

import java.util.List;

public interface PizzaReadService {

    /**
     * Get existing pizzas
     *
     * @return List<Pizza>
     */
    List<Pizza> getPizzas();

    /**
     * Get pizza by ID
     *
     * @param pizzaId ID of pizza
     * @return Pizza
     * @throws PizzaNotFoundException Pizza not found
     */
    Pizza getPizza(Long pizzaId) throws PizzaNotFoundException;

}
