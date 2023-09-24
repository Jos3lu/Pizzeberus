package com.hiberus.services;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;

import java.util.List;

public interface UserService {

    /**
     * Create new user
     *
     * @param user User to be created
     * @return User
     * @throws UserAlreadyExistsException User already exists
     */
    User createUser(User user) throws UserAlreadyExistsException;

    /**
     * Update existing user
     *
     * @param userId User ID
     * @param user User to be modified
     * @return User
     * @throws UserNotFoundException User not found
     */
    User updateUser(Long userId, User user) throws UserNotFoundException;

    /**
     * Delete user by ID
     *
     * @param userId User ID
     * @return void
     */
    void deleteUser(Long userId);

    /**
     * Get users
     *
     * @return List<User>
     */
    List<User> getUsers();

    /**
     * Get user by ID
     *
     * @param userId User ID
     * @return User
     * @throws UserNotFoundException User not found
     */
    User getUser(Long userId) throws UserNotFoundException;

}
