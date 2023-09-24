package com.hiberus.exceptions;

public class PizzaAlreadyExistsException extends Exception {

    public PizzaAlreadyExistsException(String name) {
        super("Pizza already exists: " + name);
    }

}
