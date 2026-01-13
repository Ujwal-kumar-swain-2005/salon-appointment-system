package com.example.user_service.controller;

import com.example.user_service.modal.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{userId}")
    public User getUserById(@PathVariable("userId") Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent())
            return optional.get();

        throw new Exception("User not found.");
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("User not found with ID " + id);
        }
        var existingUser = optional.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUserById(@PathVariable Long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("User not exits with ID " + id);
        }

        userRepository.deleteById(id);
        return "User deleted";
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(userService.getUserInfo(token), HttpStatus.OK);
    }
}