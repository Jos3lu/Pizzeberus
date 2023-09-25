package com.hiberus.controllers;

import com.hiberus.dtos.*;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.mappers.UserMapper;
import com.hiberus.models.User;
import com.hiberus.services.PizzaReadService;
import com.hiberus.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 409, message = "Already exists")
    })
    public ResponseEntity<UserBasicResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserBasicResponseDto userBasicResponseDto = userMapper.userToBasicResponseDto(
                    userService.createUser(userMapper.dtoCreateToUser(userCreateDto)));
            return new ResponseEntity<>(userBasicResponseDto, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{userId}")
    @ApiOperation(value = "Update an existing user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<UserGeneralResponseDto> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        try {
            return ResponseEntity.ok(userMapper.userToGeneralResponseDto(
                    userService.updateUser(userId, userMapper.dtoUpdateToUser(userUpdateDto))));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{userId}")
    @ApiOperation(value = "Delete an existing user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Get users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
    public ResponseEntity<List<UserGeneralResponseDto>> getUsers() {
        List<UserGeneralResponseDto> users = userService.getUsers().stream()
                .map(userMapper::userToGeneralResponseDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "Get user by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<UserPizzasResponseDto> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getUser(userId);
            UserPizzasResponseDto userPizzasResponseDto = userMapper
                    .userToUserPizzasResponseDto(user);

            // Get favourite pizzas of user
            userPizzasResponseDto.setPizzas(userService
                    .getFavouritePizzasUser(user.getPizzas()));

            return ResponseEntity.ok(userPizzasResponseDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/add-favourite-pizza")
    @ApiOperation(value = "Add favourite pizza to user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<UserGeneralResponseDto> addPizzaUser(@RequestParam Long userId, @RequestParam Long pizzaId) {
        try {
            UserGeneralResponseDto userGeneralResponseDto = userMapper
                    .userToGeneralResponseDto(userService.addPizzaUser(userId, pizzaId));
            return ResponseEntity.ok(userGeneralResponseDto);
        } catch (UserNotFoundException | PizzaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/delete-favourite-pizza")
    @ApiOperation(value = "Delete user's favourite pizza")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<UserGeneralResponseDto> deletePizzaUser(@RequestParam Long userId, @RequestParam Long pizzaId) {
        try {
            UserGeneralResponseDto userGeneralResponseDto = userMapper
                    .userToGeneralResponseDto(userService.deletePizzaUser(userId, pizzaId));
            return ResponseEntity.ok(userGeneralResponseDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
