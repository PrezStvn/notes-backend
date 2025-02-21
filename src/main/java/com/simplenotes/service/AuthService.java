package com.simplenotes.service;

import com.simplenotes.model.User;
import com.simplenotes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(String email, String password) {
        try {
            logger.debug("Attempting to register user with email: {}", email);
            
            if (userRepository.findByEmail(email).isPresent()) {
                logger.debug("Email already exists: {}", email);
                throw new RuntimeException("Email already registered");
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            
            logger.debug("User registered successfully: {}", email);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            logger.error("Error during registration: ", e);
            throw e;
        }
    }

    public String login(String email, String password) {
        try {
            logger.debug("Attempting login for user: {}", email);
            
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.debug("User not found: {}", email);
                    return new RuntimeException("User not found");
                });

            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.debug("Invalid password for user: {}", email);
                throw new RuntimeException("Invalid password");
            }
            
            logger.debug("Login successful for user: {}", email);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            logger.error("Error during login: ", e);
            throw e;
        }
    }
} 