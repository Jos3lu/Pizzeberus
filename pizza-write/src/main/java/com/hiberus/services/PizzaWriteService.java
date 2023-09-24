package com.hiberus.services;

import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.models.Pizza;

public interface PizzaWriteService {

    /**
     * Create a new pizza
     *
     * @param pizza Pizza to be created
     * @return Pizza
     * @throws PizzaAlreadyExistsException Pizza already exists
     */
    Pizza createPizza(Pizza pizza) throws PizzaAlreadyExistsException;

    /**
     * Modify an existing pizza
     *
     * @param pizzaId Pizza ID
     * @param pizza Pizza information
     * @return Pizza
     * @throws PizzaNotFoundException Pizza not found
     */
    Pizza updatePizza(Long pizzaId, Pizza pizza) throws PizzaNotFoundException;

}
