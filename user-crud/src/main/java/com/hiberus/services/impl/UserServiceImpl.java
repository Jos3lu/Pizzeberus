package com.hiberus.services.impl;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.existsById(user.getId())) {
            throw new UserAlreadyExistsException(user.getName());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

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
}
