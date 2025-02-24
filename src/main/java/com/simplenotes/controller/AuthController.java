package com.simplenotes.controller;

import com.simplenotes.service.AuthService;
import com.simplenotes.dto.LoginRequest;
import com.simplenotes.dto.RegisterRequest;
import com.simplenotes.dto.AuthResponse;
import com.simplenotes.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            logger.debug("Received registration request for email: {}", request.getEmail());
            String token = authService.register(request.getEmail(), request.getPassword());
            logger.debug("Registration successful, token generated");
            return ResponseEntity.ok(new AuthResponse(token, request.getEmail()));
        } catch (RuntimeException e) {
            logger.error("Registration failed: ", e);
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Email already registered"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(token, request.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid credentials"));
        }
    }
} 