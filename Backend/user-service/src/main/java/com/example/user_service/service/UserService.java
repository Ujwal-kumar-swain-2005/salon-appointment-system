package com.example.user_service.service;

import com.example.user_service.exception.UserException;
import com.example.user_service.modal.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUser(Long id) throws  UserException;
    User updateUser(Long id, User userDetails) throws  UserException;
}
