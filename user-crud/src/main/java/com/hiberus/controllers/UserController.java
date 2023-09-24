package com.hiberus.controllers;

import com.hiberus.dtos.*;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.mappers.UserMapper;
import com.hiberus.services.UserService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Modify an existing user")
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
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Get users")
    public ResponseEntity<List<UserGeneralResponseDto>> getUsers() {
        List<UserGeneralResponseDto> users = userService.getUsers().stream()
                .map(userMapper::userToGeneralResponseDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "Get user by ID")
    public ResponseEntity<UserPizzasResponseDto> getUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userMapper.userToUserPizzasResponseDto(
                    userService.getUser(userId)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/add-favourite-pizza")
    @ApiOperation(value = "Add favourite pizza to user")
    public ResponseEntity<UserGeneralResponseDto> addPizzaUser(@RequestParam Long userId, @RequestParam Long pizzaId) {
        return null;
    }

    @PutMapping(value = "/delete-favourite-pizza")
    @ApiOperation(value = "Delete user's favourite pizza")
    public ResponseEntity<UserGeneralResponseDto> deletePizzaUser(@RequestParam Long userId, @RequestParam Long pizzaId) {
        return null;
    }

}
