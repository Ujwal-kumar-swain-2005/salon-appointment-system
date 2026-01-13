package com.example.user_service.controller;

import com.example.user_service.payload.AuthResponse;
import com.example.user_service.payload.LoginDto;
import com.example.user_service.payload.SignupDto;
import com.example.user_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignupDto signupDto) throws Exception {
        var authResponse = authService.signup(signupDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) throws Exception {
        var authResponse = authService.login(loginDto.getUsername(), loginDto.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> refreshToken(@PathVariable String refreshToken) throws Exception {
        var authResponse = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(authResponse);
    }
}