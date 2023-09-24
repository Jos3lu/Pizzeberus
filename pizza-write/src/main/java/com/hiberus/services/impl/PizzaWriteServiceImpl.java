package com.hiberus.services.impl;

import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaWriteMapper;
import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaWriteRepository;
import com.hiberus.services.PizzaReadService;
import com.hiberus.services.PizzaWriteService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PizzaWriteServiceImpl implements PizzaWriteService {

    @Autowired
    private PizzaWriteRepository pizzaWriteRepository;

    @Autowired
    PizzaWriteMapper pizzaWriteMapper;

    @Autowired
    private PizzaReadService pizzaReadService;

    @Override
    public Pizza createPizza(Pizza pizza) throws PizzaAlreadyExistsException {
        if (pizzaWriteRepository.existsByName(pizza.getName())) {
            throw new PizzaAlreadyExistsException(pizza.getName());
        }
        return pizzaWriteRepository.save(pizza);
    }

    @Override
    @CircuitBreaker(name = "pizza-read", fallbackMethod = "fallBackGetPizza")
    public Pizza updatePizza(Long pizzaId, Pizza pizza) throws PizzaNotFoundException {
        Pizza oldPizza;
        try {
            oldPizza = pizzaWriteMapper.responseDtoToPizza(
                    pizzaReadService.getPizza(pizzaId).getBody());
        } catch (FeignException.NotFound e) {
            throw new PizzaNotFoundException(pizzaId);
        }

        // Set new name & save in DB
        oldPizza.setName(pizza.getName());
        return pizzaWriteRepository.save(oldPizza);
    }

    private Pizza fallBackGetPizza(Long pizzaId, Pizza pizza, Throwable throwable) throws PizzaNotFoundException {
        if (throwable.getClass().equals(PizzaNotFoundException.class)) {
            throw new PizzaNotFoundException(pizzaId);
        }

        log.info("Sent default pizza");
        return Pizza.builder().id(-1L).name("Default pizza").build();
    }
}
