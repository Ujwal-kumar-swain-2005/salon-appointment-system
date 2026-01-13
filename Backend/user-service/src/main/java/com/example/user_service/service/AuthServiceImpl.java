package com.example.user_service.service;

import com.example.user_service.modal.User;
import com.example.user_service.payload.AuthResponse;
import com.example.user_service.payload.SignupDto;
import com.example.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final KeyCloakService keyCloakService;

    public AuthServiceImpl(UserRepository userRepository, KeyCloakService keyCloakService) {
        this.userRepository = userRepository;
        this.keyCloakService = keyCloakService;
    }

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        var tokenResponse = keyCloakService.getAdminAccessToken(username, password, "password", null);

        var authResponse = new AuthResponse();
        authResponse.setJwtToken(tokenResponse.getAccessToken());
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setMessage("User logged in successfully");

        return authResponse;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) throws Exception {
        var tokenResponse = keyCloakService.getAdminAccessToken(null, null, "refresh_token", refreshToken);

        var authResponse = new AuthResponse();
        authResponse.setJwtToken(tokenResponse.getAccessToken());
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setMessage("Token refreshed successfully");

        return authResponse;
    }

    @Override
    public AuthResponse signup(SignupDto signupDto) throws Exception {
        keyCloakService.createUser(signupDto);

        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(signupDto.getPassword());
        user.setEmail(signupDto.getEmail());
        user.setRole(signupDto.getRole());
        user.setFullName(signupDto.getFullName());
        user.setCreateAt(LocalDateTime.now());

        userRepository.save(user);

        var tokenResponse = keyCloakService.getAdminAccessToken(signupDto.getUsername(), signupDto.getPassword(), "password", null);

        var authResponse = new AuthResponse();
        authResponse.setJwtToken(tokenResponse.getAccessToken());
        authResponse.setRefreshToken(tokenResponse.getRefreshToken());
        authResponse.setMessage("User registered successfully");
        authResponse.setRole(signupDto.getRole());

        return authResponse;
    }

}