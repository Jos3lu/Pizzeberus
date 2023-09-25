package com.hiberus.services.impl;

import com.hiberus.dtos.PizzaResponseDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.PizzaReadService;
import com.hiberus.services.UserService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PizzaReadService pizzaReadService;

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.existsByName(user.getName())) {
            throw new UserAlreadyExistsException(user.getName());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) throws UserNotFoundException {
        // If user not found return exception
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        // Get user & update name
        User oldUser = getUser(userId);
        oldUser.setName(user.getName());

        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    @CircuitBreaker(name = "favourites-pizzas", fallbackMethod = "fallBackGetFavouritePizzas")
    public List<PizzaResponseDto> getFavouritePizzasUser(List<Long> pizzaIds) {
        return pizzaReadService.getFavouritePizzas(pizzaIds).getBody();
    }

    @Override
    @CircuitBreaker(name = "add-favourite-pizza", fallbackMethod = "fallBackAddFavouritePizza")
    public User addPizzaUser(Long userId, Long pizzaId) throws UserNotFoundException, PizzaNotFoundException {
        // Get User by ID
        User user = getUser(userId);

        // Check if pizza exists
        try {
            pizzaReadService.getPizza(pizzaId);
        } catch (FeignException.NotFound e) {
            throw new PizzaNotFoundException(pizzaId);
        }

        // Set pizza as favourite
        user.getPizzas().add(pizzaId);
        return userRepository.save(user);
    }

    @Override
    public User deletePizzaUser(Long userId, Long pizzaId) throws UserNotFoundException {
        User user = getUser(userId);
        user.getPizzas().remove(pizzaId);
        return userRepository.save(user);
    }

    private List<PizzaResponseDto> fallBackGetFavouritePizzas(List<Long> pizzaIds, Throwable throwable) {
        log.info("Sent default favourite pizzas");
        return Collections.emptyList();
    }

    private User fallBackAddFavouritePizza(Long userId, Long pizzaId, Throwable throwable)
            throws PizzaNotFoundException {
        if (throwable.getClass().equals(PizzaNotFoundException.class)) {
            throw new PizzaNotFoundException(pizzaId);
        }

        // Send default user
        log.info("Sent default user");
        return User.builder()
                .id(-1L)
                .name("Default user")
                .pizzas(new ArrayList<>())
                .build();
    }

}
