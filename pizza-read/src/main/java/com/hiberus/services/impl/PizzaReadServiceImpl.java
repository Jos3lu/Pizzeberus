package com.hiberus.services.impl;

import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaReadRepository;
import com.hiberus.services.PizzaReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaReadServiceImpl implements PizzaReadService {

    @Autowired
    private PizzaReadRepository pizzaReadRepository;

    @Override
    public List<Pizza> getPizzas() {
        return pizzaReadRepository.findAll();
    }

    @Override
    public Pizza getPizza(Long pizzaId) throws PizzaNotFoundException {
        return pizzaReadRepository.findById(pizzaId)
                .orElseThrow(() -> new PizzaNotFoundException(pizzaId));
    }

    @Override
    public List<Pizza> getFavouritePizzas(List<Long> pizzaIds) {
        return pizzaReadRepository.findAllById(pizzaIds);
    }
}
