package com.example.user_service.impl;

import com.example.user_service.exception.UserException;
import com.example.user_service.modal.User;
import com.example.user_service.payload.KeyCloakUserDto;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.KeyCloakService;
import com.example.user_service.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeyCloakService cloakService;

    public UserServiceImpl(UserRepository userRepository, KeyCloakService cloakService) {
        this.userRepository = userRepository;
        this.cloakService = cloakService;
    }


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent())

            return optional.get();

        throw new UserException("User not found with ID " + id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new UserException("User not exits with ID " + id);
        }

        userRepository.deleteById(id);

    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new UserException("User not found with ID " + id);
        }
        var existingUser = optional.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }
    @Override
    public User getUserInfo(String token) throws Exception {
        KeyCloakUserDto cloakUserDto = cloakService.fetchUserProfile(token);
        return userRepository.findByEmail(cloakUserDto.getEmail());
    }
}