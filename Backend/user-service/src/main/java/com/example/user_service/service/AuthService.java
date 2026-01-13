package com.example.user_service.service;

import com.example.user_service.payload.AuthResponse;
import com.example.user_service.payload.SignupDto;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse refreshToken(String refreshToken) throws Exception;
    AuthResponse signup(SignupDto signupDto) throws Exception;
}