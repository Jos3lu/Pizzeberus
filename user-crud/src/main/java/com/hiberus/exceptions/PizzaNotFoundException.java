package com.hiberus.exceptions;

public class PizzaNotFoundException extends Exception {

    public PizzaNotFoundException(Long pizzaId) {
        super("Pizza not found: " + pizzaId);
    }
}
